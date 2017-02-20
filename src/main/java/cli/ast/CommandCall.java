package cli.ast;

import cli.exceptions.NoCommandNameException;
import cli.visitors.Visitor;

import java.util.List;

/**
 * Represents one call in pipeline.
 * Holds command name and list of its arguments
 */
public class CommandCall extends Node {
    public StringValue commandName;
    public List<StringValue> arguments;

    public CommandCall(List<StringValue> stringValues) throws NoCommandNameException {
        if (stringValues.isEmpty()) {
            throw new NoCommandNameException("No command to execute");
        }
        this.commandName = stringValues.get(0);
        this.arguments = stringValues.subList(1, stringValues.size());
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }
}
