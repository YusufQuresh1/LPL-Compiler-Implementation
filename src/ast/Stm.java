package ast;

/**
 * The abstract parent type for all statement AST classes.
 */
public abstract class Stm extends AST {


    /**
     * Emit SSM assembly code which implements this statement.
     */
    public abstract void compile();
}
