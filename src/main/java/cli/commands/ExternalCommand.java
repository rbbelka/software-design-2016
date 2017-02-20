package cli.commands;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.List;

/**
 * Calls external command from /bin/sh with given arguments
 *
 */
public class ExternalCommand implements Command {
    private String commandName;

    public ExternalCommand(String commandName) {
        this.commandName = commandName;
    }

    /**
     * Tries to execute command as external
     *
     * @param args arguments for command call
     * @param input unused, command ignores input from pipe
     * @return Output of external call if no errors happen, or empty string
     */
    @Override
    public String execute(List<String> args, String input) {
        String output;
        String arguments = StringUtils.join(args, " ");
        final ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", commandName + " " + arguments);

        try
        {
            final Process p = pb.start();
            final ProcessResultReader stderr = new ProcessResultReader(p.getErrorStream(), "STDERR");
            final ProcessResultReader stdout = new ProcessResultReader(p.getInputStream(), "STDOUT");
            stderr.start();
            stdout.start();
            final int exitValue = p.waitFor();
            if (exitValue == 0)
            {
                output = stdout.toString();
            }
            else
            {
                output = "";
                System.err.println(stderr.toString());
            }
        }
        catch (final IOException | InterruptedException e)
        {
            output = "";
            System.err.println(e.getMessage());
        }

        return output;
    }


    /**
     * Got this class from here:
     * http://stackoverflow.com/questions/7770094/executing-another-java-program-from-our-java-program/7770167
     */
    private class ProcessResultReader extends Thread
    {
        final InputStream is;
        final String type;
        final StringBuilder sb;

        ProcessResultReader(final InputStream is, String type)
        {
            this.is = is;
            this.type = type;
            this.sb = new StringBuilder();
        }

        public void run()
        {
            try
            {
                final InputStreamReader isr = new InputStreamReader(is);
                final BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null)
                {
                    this.sb.append(line).append("\n");
                }
            }
            catch (final IOException ioe)
            {
                System.err.println(ioe.getMessage());
                throw new RuntimeException(ioe);
            }
        }

        @Override
        public String toString()
        {
            return this.sb.toString();
        }
    }

}
