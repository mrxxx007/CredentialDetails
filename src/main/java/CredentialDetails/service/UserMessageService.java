package CredentialDetails.service;

import javax.swing.*;

/**
 * Created by Admin on 08.05.2017.
 */
public class UserMessageService {
    public static void displayInfoMessage(String message) {
        displayInfoMessage(message, "Info");
    }

    public static void displayInfoMessage(String message, String title) {
        displayMessage(message, JOptionPane.INFORMATION_MESSAGE, title);
    }

    public static void displayWarningMessage(String message) {
        displayMessage(message, JOptionPane.WARNING_MESSAGE, "Warning");
    }

    public static void displayErrorMessage(String message) {
        displayMessage(message, JOptionPane.ERROR_MESSAGE, "Application Error");
    }


    private static void displayMessage(String message, int messageType, String title) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
}
