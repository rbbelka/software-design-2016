package cli.commands;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author natalia on 09.10.16.
 */
public class PwdCommandTest {

    private PwdCommand pwd = new PwdCommand();

    @Test
    public void testPwd() throws Exception {
        List<String> args = new ArrayList<>();
        String output = pwd.execute(args, "");
        assertEquals("If test doesn't pass, check project directory", "/home/natalia/au/sd/software-design-2016", output);
    }

}