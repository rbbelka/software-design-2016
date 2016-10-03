package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    /*
    *  REPL
    *  reads line from input and handles it
    */
    public static void main(String[] args) throws IOException {

        String line;

        while (true) {
            System.out.print("$ ");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            line = reader.readLine();

            if (line.split(" ")[0].equals("exit"))
                break;

            handle(line);
        }
    }

    public static String handle(String line) {
        Command command = Parser.parse(line);
        return "temp";
    }
}
