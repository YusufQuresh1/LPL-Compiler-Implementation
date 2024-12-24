package ast;

public class StmWhile extends Stm {

    public Exp exp;

    public final Stm body;

    public StmWhile(Exp exp, Stm body) {
        this.exp = exp;
        this.body = body;
    }

    @Override
    public void compile() {
        String loopStartLabel = freshName("while_start");
        String loopEndLabel = freshName("while_end");
        emit(loopStartLabel + ":");
        if (exp == null) {
            exp = new ExpInteger(0);
        }
        exp.compile();
        emit("push " + loopEndLabel, "jump_z");
        if (body != null) {
            body.compile();
        }
        emit("push " + loopStartLabel, "jump");
        emit(loopEndLabel + ":");
    }
}
