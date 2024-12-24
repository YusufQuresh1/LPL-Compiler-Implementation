package ast;

public class ExpNot extends Exp {

    public final Exp e;

    public ExpNot(Exp e) {
        this.e = e;
    }

    @Override
    public void compile() {
        // TODO
        e.compile();
        emit("not");
        throw new UnsupportedOperationException();
    }
}
