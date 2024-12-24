package ast;

public class StmPrintChar extends Stm {

    public final Exp exp;

    public StmPrintChar(Exp exp) {
        this.exp = exp;
    }

    @Override
    public void compile() {
        exp.compile();
        emit("push 1", "sysc");
    }
}
