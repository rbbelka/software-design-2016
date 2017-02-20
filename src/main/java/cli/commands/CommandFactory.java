package cli.commands;

import cli.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for {@link Command}
 */
public class CommandFactory {
    private static final Map<String, Command> commandMap = new HashMap<>();

    static  {
        commandMap.put("cat", new CatCommand());
        commandMap.put("echo", new EchoCommand());
        commandMap.put("wc", new WcCommand());
        commandMap.put("pwd", new PwdCommand());
        commandMap.put("exit", new ExitCommand());
        commandMap.put("grep", new GrepCommand());
        commandMap.put("ls", new LsCommand());
        commandMap.put("cd", new CdCommand());
    }


    /**
     * @param commandName - name of command needs to be created
     * @return Command from commandMap if key equals to commandName present, or
     *          {@link ExternalCommand}
     */
    public static Command createCommand(String commandName) {
        Command command = commandMap.get(commandName);
        if (command == null) {
            command = new ExternalCommand(commandName);
        }
        return command;
    }
}