package compile;

import ast.AST;
import ast.Program;
import parse.LPLParser;

import java.io.IOException;
import java.nio.file.Paths;

import static parse.LPLParser.SBNF_FILE;

/** Compile an LPL program to SSM assembly code.  */
public class LPLCompiler {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: compile.LPLCompiler <source-file> <SSM-assembly-file>");
            System.exit(1);
        }
        System.out.println("Lexing with token defs from file " + SBNF_FILE);
        LPLParser parser = new LPLParser(SBNF_FILE);
        System.out.println("Parsing source file " + args[0]);
        Program teenyProg = parser.parse(args[0]);
        System.out.println("Syntax is OK");
        teenyProg.compile();
        AST.write(Paths.get(args[1]));
        System.out.println("Assembly code written to " + args[1]);
    }
}