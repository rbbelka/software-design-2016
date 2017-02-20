package cli.commands;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.apache.commons.io.FileUtils.readFileToString;

/**
 * command returns its input
 */
public class CatCommand implements Command {

    public String execute(List<String> args, String input) {
        if (args.isEmpty()) {
            if (input == null)
                return "";
            return input;
        }

        try {
            return new String(Files.readAllBytes(Paths.get(args.get(0))));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }
}
