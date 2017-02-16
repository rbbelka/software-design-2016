package cli.visitors;

import cli.Environment;
import cli.ast.*;
import cli.commands.Command;
import cli.commands.CommandFactory;
import com.sun.deploy.util.StringUtils;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Executes given AST
 */
public class Executor implements Visitor {
    private final PrintStream out;
    private final PrintStream err;
    private Environment env = Environment.getInstance();
    private String result;

    public Executor(PrintStream out, PrintStream err) {
        this.out = out;
        this.err = err;
    }

    @Override
    public void visit(Assignment assignment) {
        assignment.stringValue.visit(this);
        env.putValue(assignment.varName, result);
    }

    @Override
    public void visit(CommandCall commandCall) {
        String input = result;
        commandCall.commandName.visit(this);
        Command command = CommandFactory.createCommand(result);

        List<String> arguments = new ArrayList<>();
        for (StringValue argument : commandCall.arguments) {
            argument.visit(this);
            arguments.add(result);
        }
        result = command.execute(arguments, input);
    }

    @Override
    public void visit(Literal literal) {
        List<String> resultList = new ArrayList<>();
        for (StringUnit unit : literal.units) {
            unit.visit(this);
            resultList.add(result);
        }
        result = StringUtils.join(resultList, " ");
    }

    @Override
    public void visit(Pipeline pipeline) {
        result = "";
        for (CommandCall commandCall : pipeline.commandCalls) {
            commandCall.visit(this);
        }
        out.println(result);
    }

    @Override
    public void visit(Substitution substitution) {
        String value = env.getValue(substitution.varName);
        if (value == null) {
            value = "";
            err.println("Unknown variable " + substitution.varName);
        }
        result = value;
    }

    @Override
    public void visit(Text text) {
        result = text.text;
    }
}
