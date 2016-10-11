package cli.commands;

import cli.Command;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.apache.commons.io.FileUtils.readFileToString;

/**
 * @author natalia on 03.10.16.
 */
public class CatCommand extends Command {

    protected String execute(List<String> args, String input) {
        if (args.size() == 0) {
            if (input == null)
                return "";
            return input;
        }

        try {
            return new String(Files.readAllBytes(Paths.get(args.get(0))));
        } catch (IOException e) {
            //TODO deal with exception
            e.printStackTrace();
        }

        return "";
    }

}
