package ast;

public class ExpInteger extends Exp {
    public final int value;

    public ExpInteger(int value) {
        this.value = value;
    }

    @Override
    public void compile() {
        emit("push " + value);
    }
}
