package cli.commands;

import java.io.PrintStream;
import java.util.List;

/**
 *
 */
public interface Command {
    String execute(List<String> args, String input);
}
