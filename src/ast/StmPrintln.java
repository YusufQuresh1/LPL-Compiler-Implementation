package ast;

public class StmPrintln extends Stm {

    public final Exp exp;

    public StmPrintln(Exp exp) {
        this.exp = exp;
    }

    @Override
    public void compile() {
        if (exp != null) {
            exp.compile();
        }
        emit("push 3", "sysc", "push 2", "sysc");
    }
}
