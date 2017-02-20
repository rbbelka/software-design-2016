package cli.commands;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author natalia on 11.10.16.
 */
public class CatCommandTest {


    CatCommand cat = new CatCommand();

    @Test
    public void testWcInput() throws Exception {
        List<String> args = new ArrayList<>();
        String input = "one two    three ";
        String output = cat.execute(args, input);
        assertEquals("result should be " + input + " instead of " + output, input, output);
    }

    @Test
    public void testWcArg() throws Exception {
        List<String> args = Arrays.asList("src/test/resources/wcResource", "test");
        String input = "one two    three ";
        String output = cat.execute(args, input);
        String result = "one two\n" + "three           four\n" + "\n" + "b";
        assertEquals("result should be " + input + " instead of " + output, result, output);
    }

    @Test
    public void testWcNoInputOrArgs() throws Exception {
        List<String> args = new ArrayList<>();
        String output = cat.execute(args, null);
        assertEquals("result with no input should be empty string", "", output);
    }
}
