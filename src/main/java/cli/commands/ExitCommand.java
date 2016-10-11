package cli.commands;

import cli.Command;

import java.util.List;

/**
 * command terminates the app
 */
public class ExitCommand extends Command {

    protected String execute(List<String> args, String input) {
        System.exit(0);
        return null;
    }
}
