package cli.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Changes working directory
 */
public class CdCommand implements Command {
    @Override
    public String execute(List<String> args, String input) {
        String currentDir = System.getProperty("user.dir");
        if(!args.isEmpty()) {
            Path path = Paths.get(currentDir).resolve(args.get(0));
            if(!Files.isDirectory(path)) {
                System.err.println("Incorrect path");
            } else {
                System.setProperty("user.dir", path.toString());
            }
        }
        return "";
    }
}
