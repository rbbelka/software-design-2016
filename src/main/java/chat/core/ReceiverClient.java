package chat.core;

public interface ReceiverClient {
    // Will be called from receiver's working threads. Should be thread-safe
    void processMessage(String senderName, String senderIp, String message);
}
