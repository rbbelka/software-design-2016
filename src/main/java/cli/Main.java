package cli;

import cli.ast.Node;
import cli.exceptions.SyntaxErrorException;
import cli.visitors.Executor;
import cli.visitors.Printer;

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
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            line = reader.readLine();

            handle(line);
        }
    }

    private static void handle(String line) {
        try {
            Node root = Parser.parse(line);
            root.visit(new Executor(System.out, System.err));
            System.out.flush();
            System.err.flush();
        } catch (SyntaxErrorException e) {
            System.err.println("Syntax error: " + e.getMessage());
        }
    }
}
