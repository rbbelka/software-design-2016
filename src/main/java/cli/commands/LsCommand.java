package cli.commands;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

/**
 * List files in current directory
 */
public class LsCommand implements Command {
    @Override
    public String execute(List<String> args, String input) {
        StringBuilder result = new StringBuilder();
        String[] files = new File (System.getProperty("user.dir")).list();
        if (files != null) {
            Stream.of(files).forEach(file -> result.append(file).append('\n'));
        }
        return result.toString();
    }
}
