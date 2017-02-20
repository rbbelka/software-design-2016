package cli.commands;

import java.io.PrintStream;
import java.util.List;

/**
 * command terminates the app
 */
public class ExitCommand implements Command {

    public String execute(List<String> args, String input) {
        System.exit(0);
        return null;
    }
}
