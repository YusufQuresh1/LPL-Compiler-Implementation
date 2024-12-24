package ast;

public class LExprVar extends LExpr {

    public final String varName;

    public LExprVar(String varName) {
        this.varName = varName;
    }

    @Override
    public void compile() {
        // TODO: extend to handle stack-allocated variables
        // global variable, statically allocated
        String safeName = makeSafe(varName);
        globals.add(safeName);
        emit("push " + safeName);
        //emit("load");
    }
}
