package CredentialDetails.app;

import CredentialDetails.forms.MainForm;

import javax.swing.*;

/**
 * Application startup class with single instance of a Main Frame
 */
public class Application implements Runnable {
    private static MainForm applicationInstance;

    public static synchronized MainForm getInstance() {
        if (applicationInstance == null) {
            applicationInstance = new MainForm();
        }
        return applicationInstance;
    }

    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Credential Details");
        frame.setContentPane(getInstance().$$$getRootComponent$$$());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
