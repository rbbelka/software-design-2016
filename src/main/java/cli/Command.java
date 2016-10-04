package cli;

import java.util.List;

/**
 * @author natalia on 02.10.16.
 */
public abstract class Command {

    protected abstract String execute(List<String> args, String input);

}
