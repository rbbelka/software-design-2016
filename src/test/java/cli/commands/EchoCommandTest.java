package cli.commands;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author natalia on 10.10.16.
 */
public class EchoCommandTest {

    EchoCommand echo = new EchoCommand();

    @Test
    public void testEcho() throws Exception {
        List<String> args = Arrays.asList("one", "two", "three");
        assertEquals("one two three", echo.execute(args, ""));
    }

    @Test
    public void testEchoNoArgs() throws Exception {
        List<String> args = new ArrayList<>();
        assertEquals("", echo.execute(args, "input"));
    }

    @Test
    public void testEchoWithSpaces() throws Exception {
        List<String> args = Arrays.asList("one", "two", "three and four");
        assertEquals("one two three and four", echo.execute(args, ""));
    }
}