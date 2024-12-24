package ast;

public class StmIf extends Stm {

    public Exp exp;

    public final Stm trueBranch, falseBranch;

    public StmIf(Exp exp, Stm trueBranch, Stm falseBranch) {
        this.exp = exp;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public void compile() {
        String ifFalseLabel = freshName("if_false");
        String ifEndLabel = freshName("if_end");
        if (exp == null) {
            exp = new ExpInteger(0);
        }
        exp.compile();

        emit("push " + ifFalseLabel, "jump_z");
        if (trueBranch != null) {
            trueBranch.compile();
        }
        emit("push " + ifEndLabel, "jump");
        emit(ifFalseLabel + ":");
        if (falseBranch != null) {
            falseBranch.compile();
        }
        emit(ifEndLabel + ":");
    }
}
