package chat.core;

import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sender {
    private final int port;
    private final ExecutorService threadPool;

    public Sender(int port, int threadCount) {
        this.port = port;
        threadPool = Executors.newFixedThreadPool(threadCount);
    }

    public void sendMessage(final String receiverIp,
                            final String senderName, final String message,
                            final SendingStatusCallback statusCallback) {
        threadPool.submit(new Runnable() {
            public void run() {
                try {
                    Socket socket = new Socket(receiverIp, port);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.print(createMessageData(senderName, message));
                    out.flush();
                    socket.close();
                    statusCallback.processSendingStatus(true, null);
                } catch (IOException e) {
                    statusCallback.processSendingStatus(false, e.getMessage());
                }
            }
        });
    }

    public void stop() {
        Utils.shutdownThreadPool(threadPool);
    }

    static private String createMessageData(String senderName, String message) {
        return new JSONObject()
                .put("name", senderName)
                .put("message", message)
                .toString();
    }
}
