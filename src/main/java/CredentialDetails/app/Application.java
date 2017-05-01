package CredentialDetails.app;

import CredentialDetails.controller.DataController;
import CredentialDetails.controller.FileOperationController;
import CredentialDetails.forms.MainForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
        frame.setJMenuBar(constructMenu());
        frame.pack();
        frame.setVisible(true);
    }

    private JMenuBar constructMenu() {
        JMenuBar menuBar = new JMenuBar();

        // File fileMenu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setActionCommand(ActionCommand.NEW_FILE.name());
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setActionCommand(ActionCommand.OPEN_FILE.name());
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setActionCommand(ActionCommand.SAVE_FILE.name());
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand(ActionCommand.EXIT.name());

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        setActionListenerForAllItems(fileMenu, new FileOperationController());

        // Sections menu
        JMenu sectionsMenu = new JMenu("Sections");
        JMenuItem addSectionItem = new JMenuItem("Add new section");
        JMenuItem removeSectionItem = new JMenuItem("Remove section...");

        sectionsMenu.add(addSectionItem);
        sectionsMenu.add(removeSectionItem);

        // Credentials menu
        JMenu credentialsMenu = new JMenu("Credentials");
        JMenuItem addCredentialItem = new JMenuItem("Add new");
        addCredentialItem.setActionCommand(ActionCommand.NEW_CREDENTIAL.name());
        JMenuItem removeCredentialItem = new JMenuItem("Remove selected");
        removeCredentialItem.setActionCommand(ActionCommand.REMOVE_CREDENTIAL.name());

        credentialsMenu.add(addCredentialItem);
        credentialsMenu.add(removeCredentialItem);
        setActionListenerForAllItems(credentialsMenu, new DataController());


        menuBar.add(fileMenu);
        menuBar.add(sectionsMenu);
        menuBar.add(credentialsMenu);

        return menuBar;
    }

    private void setActionListenerForAllItems(JMenu menu, ActionListener listener) {
        for (Component component : menu.getMenuComponents()) {
            if (component instanceof JMenuItem) {
                ((JMenuItem) component).addActionListener(listener);
            }
        }
    }
}
