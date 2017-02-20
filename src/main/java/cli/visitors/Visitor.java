package cli.visitors;

import cli.ast.*;

/**
 * @author natalia on 16.02.17.
 */
public interface Visitor {
    void visit(Assignment assignment);
    void visit(CommandCall commandCall);
    void visit(Literal literal);
    void visit(Pipeline pipeline);
    void visit(Substitution substitution);
    void visit(Text text);
}
