package cli.ast;

import cli.visitors.Visitor;

/**
 * Represents node of input AST
 */
public abstract class Node {
    public abstract void visit(Visitor visitor);
}
