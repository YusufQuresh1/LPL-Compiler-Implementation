package ast;

public class StmNewline extends Stm {

    public StmNewline() {}

    @Override
    public void compile() {
        emit("push 2", "sysc");
    }
}
