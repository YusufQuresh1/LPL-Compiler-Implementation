package ast;

public class StmPrint extends Stm {

    public final Exp exp;

    public StmPrint(Exp exp) {
        this.exp = exp;
    }

    @Override
    public void compile() {
        exp.compile();
        emit("push 3", "sysc");
    }
}
