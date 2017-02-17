package chat;

import chat.core.Receiver;
import chat.core.ReceiverClient;
import chat.core.Sender;
import chat.core.SendingStatusCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App
{
    private final static int PORT = 8888;

    public static void main(String[] args) {
        try {
            if (args.length == 3 && args[0].equals("-s")) {
                runSender(args[1], args[2]);
            } else if (args.length == 1 && args[0].equals("-r")) {
                runReceiver();
            } else {
                System.out.println("Usage:");
                System.out.println("\tAPP -s IP NAME");
                System.out.println("\tAPP -r");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void runSender(String receiverIp, String name) throws IOException {
        Sender sender = new Sender(PORT, 4);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("> ");
            String line = in.readLine();
            sender.sendMessage(receiverIp, name, line, new SendingStatusCallback() {
                public void processSendingStatus(boolean sent, String statusMessage) {
                    if (!sent) {
                        System.err.println(statusMessage);
                        System.err.flush();
                    }
                }
            });
        }
    }

    private static void runReceiver() throws IOException {
        Receiver receiver = new Receiver(PORT, 4, new ReceiverClient() {
            public void processMessage(String senderName, String senderIp, String message) {
                System.out.println(senderName + " (" + senderIp + "): " + message);
            }
        });
    }
}
