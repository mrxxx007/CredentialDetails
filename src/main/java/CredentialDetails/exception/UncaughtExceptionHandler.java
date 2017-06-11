package CredentialDetails.exception;

import CredentialDetails.service.UserMessageService;

import java.awt.*;

/**
 * Event handler for all uncaught exceptions in the application
 */
public class UncaughtExceptionHandler extends EventQueue {
    @Override
    protected void dispatchEvent(AWTEvent event) {
        try {
            super.dispatchEvent(event);
        } catch (Throwable t) {
            t.printStackTrace();

            String message = t.getMessage();
            if (message == null || message.isEmpty()) {
                message = t.toString();
            }

            // TODO: add stacktrace (to a log file or to the message as a hidden block)
            UserMessageService.displayErrorMessage(message);
        }
    }
}
