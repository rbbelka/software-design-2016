package cli.exceptions;

/**
 * @author natalia on 16.02.17.
 */
public class SyntaxErrorException extends CliException {
    public SyntaxErrorException(String s) {
        super(s);
    }
}
