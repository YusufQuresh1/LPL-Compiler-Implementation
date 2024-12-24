package ast;

public class ExpArithOp extends Exp {

    public final String operator;
    public Exp left;
    public Exp right;

    public ExpArithOp(String operator, Exp left, Exp right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public void compile() {
        left.compile();
        right.compile();

        switch (operator) {
            case "MUL" -> {
                emit("mul");
                return;
            }
            case "DIV" -> {
                emit("div");
                return;
            }
            case "ADD" -> {
                emit("add");
                return;
            }
            case "SUB" -> {
                emit("sub");
                return;
            }
            case "<" -> {
                emit("lt");
                return;
            }
            default -> throw new IllegalStateException("Unsupported arithmetic operator: " + operator);
        }
    }
}
