package cli.commands;

import java.io.PrintStream;
import java.util.List;

/**
 * command outputs current working directory
 */
public class PwdCommand implements Command {

    public String execute(List<String> args, String input) {
        return System.getProperty("user.dir");
    }
}
