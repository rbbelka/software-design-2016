package cli.ast;

import cli.visitors.Visitor;

/**
 * Represents assignment node of AST.
 * Holds name of variable and value to put into variable.
 */
public class Assignment extends Node {
    public final String varName;
    public final StringValue stringValue;

    public Assignment(String varName, StringValue stringValue) {
        this.varName = varName;
        this.stringValue = stringValue;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }
}
