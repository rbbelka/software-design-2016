package chat.core;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Receiver {
    private final ReceiverClient client;
    private final ExecutorService threadPool;
    private final ServerSocket serverSocket;

    public Receiver(int port, int threadCount, ReceiverClient client) throws IOException {
        if (threadCount < 2) {
            throw new IllegalArgumentException("threadCount must be greater than 1");
        }

        this.threadPool = Executors.newFixedThreadPool(threadCount);
        this.serverSocket = new ServerSocket(port);
        this.client = client;

        runListener();
    }

    public void stop() throws IOException {
        serverSocket.close();
        Utils.shutdownThreadPool(threadPool);
    }

    private void runListener() {
        threadPool.submit(new Runnable() {
            public void run() {
                while (true) {
                    Socket clientSocket;
                    try {
                        clientSocket = serverSocket.accept();
                    } catch (IOException e) {
                        break;
                    }
                    handleClient(clientSocket);
                }
            }
        });
    }

    private void handleClient(final Socket clientSocket) {
        threadPool.submit(new Runnable() {
            public void run() {
                try {
                    JSONObject json = new JSONObject(new JSONTokener(clientSocket.getInputStream()));
                    String senderName = json.getString("name");
                    String message = json.getString("message");

                    String senderIp = ((InetSocketAddress) clientSocket.getRemoteSocketAddress()).getHostName();
                    clientSocket.close();

                    client.processMessage(senderName, senderIp, message);
                } catch (IOException ignored) {
                } catch (JSONException ignored) {
                }
            }
        });
    }
}
