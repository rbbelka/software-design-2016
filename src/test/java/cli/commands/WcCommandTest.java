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
    public void testWcInput() throws Exception {
        List<String> args = new ArrayList<>();
        String input = "one two    three ";
        String output = wc.execute(args, input);
        assertEquals("result should be 3 instead of " + output, "3", output);
    }

    @Test
    public void testWcArg() throws Exception {
        List<String> args = Arrays.asList("src/test/resources/wcResource", "test");
        String input = "one two    three ";
        String output = wc.execute(args, input);
        assertEquals("result should be 5 instead of " + output, "5", output);
    }

    @Test
    public void testWcNoInputOrArgs() throws Exception {
        List<String> args = new ArrayList<>();
        String output = wc.execute(args, null);
        assertEquals("result with no input should be 0", "0", output);
    }
}