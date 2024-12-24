package ast;

public class StmAssign extends Stm {

    public final LExpr lExpr;
    public final Exp exp;

    public StmAssign(LExpr lExpr, Exp exp) {
        this.lExpr = lExpr;
        this.exp = exp;
    }

    @Override
    public void compile() {
        // TODO
        lExpr.compile();
        exp.compile();
        emit("swap");
        emit("store");
        //throw new UnsupportedOperationException();
    }
}
