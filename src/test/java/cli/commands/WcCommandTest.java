package cli.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author natalia on 04.10.16.
 */
public class WcCommandTest {

    WcCommand wc = new WcCommand();

    @Test
    public void testExecuteInput() throws Exception {
        List<String> args = new ArrayList<>();
        String input = "one two    three ";
        String output = wc.execute(args, input);
        assertTrue("result shoud be 3 instead of " + output, output.equals("3"));
    }

    @Test
    public void testExecuteArg() throws Exception {
        List<String> args = Arrays.asList("src/test/resources/wcResource", "test");
        String input = "one two    three ";
        String output = wc.execute(args, input);
        assertTrue("result shoud be 5 instead of " + output, output.equals("5"));
    }


}