package cli;

import cli.ast.*;
import cli.exceptions.NoCommandNameException;
import cli.exceptions.SyntaxErrorException;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses AST from String
 * @see Node
 *
 * |, ", ', $, = - service symbols which can not be used in literals, strings, names, etc.
 * Assignment is separate command and not allowed inside pipeline.
 */
public class Parser {

    public static Node parse(String line) throws SyntaxErrorException {
        final int firstEqualSign = line.indexOf('=');
        if (firstEqualSign == -1) {
            return parsePipeline(line);
        }
        return parseAssignment(line, firstEqualSign);
    }

    private static Assignment parseAssignment(String line, int signIndex) throws SyntaxErrorException {
        String varName = line.substring(0, signIndex);
        String value = line.substring(signIndex + 1);
        checkVarName(varName);
        StringValue stringValueNode = parseValue(value);
        return new Assignment(varName, stringValueNode);
    }

    private static void checkVarName(String varName) throws SyntaxErrorException {
        if (!varName.matches("[_a-zA-Z]\\w*")) {
            throw new SyntaxErrorException("Illegal variable name");
        }
    }

    private static StringValue parseValue(String value) throws SyntaxErrorException {
        if (value.charAt(0) == '\'' && value.charAt(value.length() - 1) == '\'') {
            return new Text(value.substring(1, value.length() - 1));
        }
        if (value.charAt(0) == '\"' && value.charAt(value.length() - 1) == '\"') {
            return parseLiteral(value.substring(1, value.length() - 1));
        }
        return parseLiteral(value);
    }

    private static Literal parseLiteral(String literal) throws SyntaxErrorException {
        List<StringUnit> units = new ArrayList<>();
        String curString = literal;
        while (!curString.isEmpty()) {
            int varStartIndex = curString.indexOf('$');
            if (varStartIndex < 0) {
                units.add(new Text(curString));
                break;
            }
            if (varStartIndex > 0) {
                units.add(new Text(curString.substring(0, varStartIndex)));
            }
            curString = curString.substring(varStartIndex + 1);
            int varEndIndex = curString.indexOf(' ');
            if (varEndIndex < 0) {
                varEndIndex = curString.length();
            }
            String varName = curString.substring(0, varEndIndex);
            checkVarName(varName);
            units.add(new Substitution(varName));
            curString = curString.substring(varEndIndex);
        }
        return new Literal(units);
    }

    private static Node parsePipeline(String line) throws SyntaxErrorException {
        List<CommandCall> commandCalls = new ArrayList<>();
        for (String item: line.split("[|]")) {
            try {
                commandCalls.add(parseCommandCall(item));
            } catch (NoCommandNameException e) {
             //empty pipeline
            }
        }
        return new Pipeline(commandCalls);
    }

    private static CommandCall parseCommandCall(String line) throws SyntaxErrorException, NoCommandNameException {
        List<StringValue> stringValues = new ArrayList<>();

        StringBuilder curStringValue = new StringBuilder();
        boolean singleQuotes = false;
        boolean doubleQuotes = false;
        for (char chr: line.toCharArray()) {
            final boolean space = Character.isSpaceChar(chr);
            if (!space || singleQuotes || doubleQuotes) {
                curStringValue.append(chr);
            }
            if ((space && !singleQuotes && !doubleQuotes)
                    || (singleQuotes && chr == '\'')
                    || (doubleQuotes && chr == '"')) {
                if (curStringValue.length() > 0) {
                    stringValues.add(parseValue(curStringValue.toString()));
                    curStringValue.setLength(0);
                }
            }
            singleQuotes = singleQuotes != (chr == '\'');
            doubleQuotes = doubleQuotes != (chr == '"');
        }
        if (singleQuotes || doubleQuotes) {
            throw new SyntaxErrorException("Quotes are not closed");
        }
        if (curStringValue.length() > 0) {
            stringValues.add(parseLiteral(curStringValue.toString()));
        }

        return new CommandCall(stringValues);
    }
}
