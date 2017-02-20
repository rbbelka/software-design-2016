package chat.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

class Utils {
    static void shutdownThreadPool(ExecutorService threadPool) {
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
            try {
                threadPool.awaitTermination(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
