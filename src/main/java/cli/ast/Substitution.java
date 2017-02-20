package cli.ast;

import cli.visitors.Visitor;

/**
 * Holds name of variable that should be substituted by its value
 */
public class Substitution extends StringUnit {
    public String varName;

    public Substitution(String varName) {
        this.varName = varName;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }
}
