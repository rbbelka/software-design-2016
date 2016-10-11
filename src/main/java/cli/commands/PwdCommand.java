package cli.commands;

import cli.Command;

import java.util.List;

/**
 * command outputs current working directory
 */
public class PwdCommand extends Command {

    protected String execute(List<String> args, String input) {
        return System.getProperty("user.dir");
    }
}
