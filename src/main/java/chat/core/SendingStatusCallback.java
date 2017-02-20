package chat.core;

public interface SendingStatusCallback {
    // Will be called from sender's working thread. Should be thread-safe
    void processSendingStatus(boolean sent, String statusMessage);
}
