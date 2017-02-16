package cli.commands;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Finds matches of first argument in files, which paths are other arguments
 * or in input if no filepaths provided
 */
public class GrepCommand implements Command {

    @Parameter(names = "-i", description = "Case insensitivity")
    private boolean caseInsensitive = false;

    @Parameter(names = "-w", description = "Search by full words")
    private boolean fullWords = false;

    @Parameter(names = "-A", description = "Number of lines after match to print")
    private int linesToPrint = 0;

    @Parameter(description = "Pattern")
    private List<String> args = new ArrayList<>();


    @Override
    public String execute(List<String> argList, String input) {

        new JCommander(this, argList.toArray(new String[argList.size()]));
        if(args.isEmpty()) {
            System.err.println("Pattern is not provided");
            return "";
        }

        String arg = args.remove(0);
        if (fullWords) {
            arg = "(\\b)" + arg + "(\\b)";
        }

        Pattern pattern;
        if (caseInsensitive) {
            pattern = Pattern.compile(arg, Pattern.CASE_INSENSITIVE);
        } else {
            pattern = Pattern.compile(arg);
        }

        if (args.isEmpty()) {
            return findMatches(pattern, input);
        } else {
            StringBuilder output = new StringBuilder();
            for (String path : args) {
                try {
                    output.append(findMatches(pattern, new Scanner(new File(path)).useDelimiter("\\Z").next()));
                } catch (FileNotFoundException e) {
                    System.err.println("File " + path + " is nit found");
                }
            }
            return output.toString();
        }
    }

    private String findMatches(Pattern pattern, String input) {
        StringBuilder output = new StringBuilder();

        String[] lines = input.split("\\r?\\n");
        for(int i = 0; i < lines.length; i++) {
            if (pattern.matcher(lines[i]).find()) {
                output.append(lines[i]).append('\n');
                for (int j = 0; j < linesToPrint && i + j < lines.length; j++) {
                    output.append(lines[j]).append('\n');
                }
            }
        }
        return output.toString();
    }
}
