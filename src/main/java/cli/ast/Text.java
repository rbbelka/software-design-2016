package cli.ast;

import cli.visitors.Visitor;

/**
 * Holds simple string
 */
public class Text extends StringUnit {
    public String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }
}
