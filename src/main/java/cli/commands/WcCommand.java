package cli.commands;

import cli.Command;

import java.io.*;
import java.util.List;

/**
 * @author natalia on 03.10.16.
 */
public class WcCommand extends Command {

    protected String execute(List<String> args, String input) {

        if (args.size() == 0)
            return String.valueOf(input.replaceAll("\\s+", " ").split(" ").length);

        // TODO what if several args?
        return String.valueOf(wordCount(args.get(0)));
    }


    private static int wordCount(String path) {

        String line;
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            while ((line = reader.readLine()) != null) {
                count += line.isEmpty() ? 0 : line.replaceAll("\\s+", " ").split(" ").length;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;

    }
}
