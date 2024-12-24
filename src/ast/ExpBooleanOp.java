package ast;

public class ExpBooleanOp extends Exp {

    public final String operator;
    public Exp left;
    public Exp right;

    public ExpBooleanOp(String operator, Exp left, Exp right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public void compile() {
        // TODO
        left.compile();
        right.compile();
        switch (operator) {
            case "<":
                emit("lt");
                return;
            case "<=":
                emit("le");
                return;
            case "==":
                emit("eq");
                return;
            case "&&":
                emit("and");
                return;
            case "||":
                emit("or");
                return;
            default:
                throw new IllegalStateException("Unsupported boolean operator: " + operator);
        }
        //throw new UnsupportedOperationException();
    }
}
