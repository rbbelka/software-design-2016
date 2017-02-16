package cli.commands;

import java.io.PrintStream;
import java.util.List;

/**
 * command outputs a string of given args
 * ignores input from pipe
 */
public class EchoCommand implements Command {

    public String execute(List<String> args, String input) {
        return String.join(" ", args);
    }
}
