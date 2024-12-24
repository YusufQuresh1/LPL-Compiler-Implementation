package ast;

/**
 * The abstract parent type for all expression AST classes.
 */
public abstract class Exp extends AST {

    /**
     * Emit SSM assembly code which implements this expression.
     */
    public abstract void compile();
}
