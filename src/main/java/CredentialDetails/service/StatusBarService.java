package CredentialDetails.service;

import java.util.concurrent.BlockingQueue;

/**
 * Service class to put messages to the status bar's queue
 */
public class StatusBarService {
    private static BlockingQueue<String> queue = null;

    private StatusBarService() {}

    public static void setMessageQueue(BlockingQueue<String> messageQueue) {
        queue = messageQueue;
    }

    public static void displayMessage(String message) {
        queue.offer(message);
    }
}
