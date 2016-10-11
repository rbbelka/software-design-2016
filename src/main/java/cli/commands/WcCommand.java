package cli.commands;

import cli.Command;

import java.io.*;
import java.util.List;

/**
 * command counts words in file or input from pipe
 */

public class WcCommand extends Command {

    /**
     * if args passed - wc counts words in first arg
     * otherwise counts words in input
     * if no args or input provided returns "0"
     */
    protected String execute(List<String> args, String input) {

        if (args.size() == 0) {
            if (input == null)
                return "0";
            return String.valueOf(input.replaceAll("\\s+", " ").split(" ").length);
        }

        return String.valueOf(wordCount(args.get(0)));
    }

    /*
    * if path is file - counts words
    * otherwise returns 0
    */
    private static int wordCount(String path) {

        String line;
        int count = 0;

        File f = new File(path);
        if(!f.isFile()) {
            System.out.println(path + " : no such file");
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            while ((line = reader.readLine()) != null) {
                count += line.isEmpty() ? 0 : line.replaceAll("\\s+", " ").split(" ").length;
            }

        } catch (IOException e) {
            //TODO handle exception
            e.printStackTrace();
        }

        return count;

    }
}
