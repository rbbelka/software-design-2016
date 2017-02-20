package cli.ast;

import cli.visitors.Visitor;

import java.util.List;

/**
 * Represents pipeline which holds list of commands with its arguments
 */
public class Pipeline extends Node {
    public List<CommandCall> commandCalls;

    public Pipeline(List<CommandCall> commandCalls) {
        this.commandCalls = commandCalls;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }
}
