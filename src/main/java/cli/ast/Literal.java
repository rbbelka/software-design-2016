package cli.ast;

import cli.visitors.Visitor;

import java.util.List;

/**
 * Represents list of {@link StringUnit}
 */
public class Literal extends StringValue {
    public List<StringUnit> units;

    public Literal(List<StringUnit> units) {
        this.units = units;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }
}
