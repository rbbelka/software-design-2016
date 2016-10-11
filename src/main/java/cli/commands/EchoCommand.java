package cli.commands;

import cli.Command;

import java.util.List;

/**
 * command outputs a string of given args
 * ignores input from pipe
 */
public class EchoCommand extends Command {

    protected String execute(List<String> args, String input) {
        return String.join(" ", args);
    }
}
