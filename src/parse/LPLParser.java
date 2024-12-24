package parse;
import ast.*;
import sbnf.ParseException;
import sbnf.lex.Lexer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/** Parse an LPL program and build its AST.  */
public class LPLParser {

    /**
     * Path to an SBNF file containing the relevant token definitions.
     */
    public static final String SBNF_FILE = "data/LPL.sbnf";

    private Lexer lex;

    public LPLParser(String sbnfPath) {
        lex = new Lexer(sbnfPath);
    }

    public Program parse(String sourcePath) throws IOException {
        AST.initialise();
        lex.readFile(sourcePath);
        lex.next();
        Program prog = Program();
        if (!lex.tok().isType("EOF")) {
            throw new ParseException(lex.tok(), "EOF");
        }
        return prog;
    }

    public Program Program() {
        List<Stm> body = new LinkedList<>();
        lex.eat("BEGIN");
        while (!lex.tok().isType("END")) {
            body.add(Stm());
        }
        lex.eat("END");
        List<Stm> body2 = new LinkedList<>();
        while (!lex.tok().isType("EOF")) {
            body2.add(FunDef());
        }
        return new Program(body);
    }

    private Stm FunDef() {
        if (lex.tok().type.equals("ID")) {
            lex.next();
            lex.eat("LBR");
            Formals();
            lex.eat("RBR");
            lex.eat("LCBR");
            Locals();
            List<Stm> fdef = new LinkedList<>();
            while (!lex.tok().isType("RCBR")) {
                fdef.add(Stm());
            }
            lex.eat("RCBR");
            return null;
        } else {
            throw new ParseException("error in FunDef statements!");
        }
    }

    private void Formals() {
        if (lex.tok().type.equals("ID")) {
            lex.next();
            List<Stm> id = new LinkedList<>();
            while (!lex.tok().isType("RBR")) {
                id.add(AnotherVar());
            }
            new StmBlock(id);
        }
    }

    private void Locals() {
        if (lex.tok().type.equals("LOCAL")) {
            lex.next();
            lex.eat("ID");
            List<Stm> lcbr = new LinkedList<>();
            while (!lex.tok().isType("SEMIC")) {
                lcbr.add(AnotherVar());
            }
            lex.eat("SEMIC");
            new StmBlock(lcbr);
        }
    }

    private Stm AnotherVar() {
        lex.eat("COMMA");
        lex.eat("ID");
        return null;
    }

    private Stm Stm() {
        if (lex.tok().type.equals("ID")) {
            return StmIdFollow();
        } else if (lex.tok().type.equals("IF")) {
            lex.next();
            lex.eat("LBR");
            Exp rhs = Exp();
            lex.eat("RBR");
            Stm stm = Stm();
            lex.eat("ELSE");
            Stm stm2 = Stm();
            return new StmIf(rhs, stm, stm2);
        } else if (lex.tok().type.equals("WHILE")) {
            lex.next();
            lex.eat("LBR");
            Exp exp = Exp();
            lex.eat("RBR");
            Stm stm = Stm();
            return new StmWhile(exp, stm);
        } else if (lex.tok().type.equals("PRINTCH")) {
            lex.next();
            Exp exp = Exp();
            lex.eat("SEMIC");
            return new StmPrintChar(exp);
        } else if (lex.tok().type.equals("PRINTLN")) {
            lex.next();
            Exp exp = Exp();
            lex.eat("SEMIC");
            return new StmPrintln(exp);
        } else if (lex.tok().type.equals("PRINT")) {
            lex.next();
            Exp exp = Exp();
            lex.eat("SEMIC");
            return new StmPrint(exp);
        } else if (lex.tok().type.equals("RETURN")) {
            lex.next();
            Exp exp = Exp();
            lex.eat("SEMIC");
            return null;
        } else if (lex.tok().type.equals("NEWLINE")) {
            lex.next();
            lex.eat("SEMIC");
            return new StmNewline();
        } else if (lex.tok().type.equals("LCBR")) {
            lex.next();
            List<Stm> lcbr = new LinkedList<>();
            while (!lex.tok().isType("RCBR")) {
                lcbr.add(Stm());
            }
            lex.eat("RCBR");
            return new StmBlock(lcbr);
        } else {
            throw new ParseException("error in Stm statements!");
        }
    }

    private Stm StmIdFollow() {
        String id = lex.eat("ID");
        if (lex.tok().type.equals("LSQBR")) {
            lex.next();
            Exp();
            lex.eat("RSQBR");
            lex.eat("ASSIGN");
            Exp();
            lex.eat("SEMIC");
            return null;
        } else if (lex.tok().type.equals("ASSIGN")) {
            lex.eat("ASSIGN");
            Exp exp = Exp();
            lex.eat("SEMIC");
            return new StmAssign(new LExprVar(id), exp);
        } else if (lex.tok().type.equals("LBR")) {
            lex.eat("LBR");
            Actuals();
            lex.eat("RBR");
            lex.eat("SEMIC");
            return null;
        } else {
            throw new ParseException("error in StmIdFollow statements!");
        }
    }

    private Exp Exp() {
        Exp e1 = SimpleExp();
        return OperatorClause(e1);
    }

    private void ExpIdFollow() {
        if (lex.tok().type.equals("LBR")) {
            lex.next();
            Actuals();
            lex.eat("RBR");
        } else if (lex.tok().type.equals("LSQBR")) {
            lex.next();
            Exp();
            lex.eat("RSQBR");
        } else if (lex.tok().type.equals("DOT")) {
            lex.eat("DOT");
            lex.eat("LENGTH");
        }
    }

    private Exp SimpleExp() {
        if (lex.tok().type.equals("ID")) {
            String var = lex.tok().image;
            lex.next();
            ExpIdFollow();
            return new ExpDereference(new LExprVar(var));
        } else if (lex.tok().type.equals("INTLIT")) {
            String intLit = lex.tok().image;
            int intValue = Integer.parseInt(intLit);
            lex.next();
            return new ExpInteger(intValue);
        } else if (lex.tok().type.equals("LBR")) {
            lex.next();
            Exp exp = Exp();
            lex.eat("RBR");
            return exp;
        } else if (lex.tok().type.equals("NEW")) {
            lex.next();
            lex.eat("LSQBR");
            Exp exp = Exp();
            lex.eat("RSQBR");
            return exp;
        } else if (lex.tok().type.equals("NOT")) {
            lex.next();
            return SimpleExp();
        } else {
            throw new ParseException("error in SimpleExp statements");
        }
    }

    private List<Exp> Actuals() {
        if (lex.tok().type.equals("RBR")) {
            return null;
        } else {
            Exp();
            List<Exp> act = new LinkedList<>();
            while (!lex.tok().isType("RBR")) {
                act.add(AnotherActual());
            }
            return act;
        }
    }

    private Exp AnotherActual() {
        lex.eat("COMMA");
        return Exp();
    }

    private Exp OperatorClause(Exp e1) {
        String arOp = lex.tok().type;
        if (arOp.equals("ADD") || arOp.equals("MUL") || arOp.equals("DIV") || arOp.equals("SUB")) {
            lex.next();
            Exp ar = SimpleExp();
            return new ExpArithOp(arOp, e1, ar);
        } else if (arOp.equals("LT") || arOp.equals("LE") || arOp.equals("EQ") || arOp.equals("AND") || arOp.equals("OR")) {
            lex.next();
            Exp exp = SimpleExp();
            return new ExpBooleanOp(arOp, e1, exp);
        } else {
            return e1;
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: parse.LPLParser <source-file>");
            System.exit(1);
        }
        System.out.println("Lexing with token defs from file " + SBNF_FILE);
        LPLParser parser = new LPLParser(SBNF_FILE);
        System.out.println("Parsing source file " + args[0]);
        parser.parse(args[0]);
        System.out.println("... parse succeeded.");
    }
}