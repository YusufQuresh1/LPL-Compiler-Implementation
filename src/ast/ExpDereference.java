package ast;

public class ExpDereference extends Exp {

    public final LExpr lExpr;

    public ExpDereference(LExpr lExpr) {
        this.lExpr = lExpr;
    }

    @Override
    public void compile() {
        lExpr.compile();
        emit("load");
    }
}
