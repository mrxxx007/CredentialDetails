package CredentialDetails.app;

import CredentialDetails.controller.DataController;
import CredentialDetails.controller.FileOperationController;
import CredentialDetails.exception.UncaughtExceptionHandler;
import CredentialDetails.forms.MainForm;
import CredentialDetails.service.StatusBarService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Application startup class with single instance of a Main Frame
 */
public class Application {
    private static MainForm mainForm;
    private static Application instance;
    private static JFrame mainFrame;
    private static BlockingQueue<String> statusBarMessageQueue;

    static {
        instance = new Application();
        statusBarMessageQueue = new ArrayBlockingQueue<>(25);
    }

    private Application() { }

    public static Application getInstance() {
        return instance;
    }


    public synchronized MainForm getMainForm() {
        if (mainForm == null) {
            mainForm = new MainForm();
        }
        return mainForm;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void run() {
        SwingUtilities.invokeLater(() -> {
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
            Toolkit.getDefaultToolkit().getSystemEventQueue().push(new UncaughtExceptionHandler());

            mainFrame = new JFrame("Credential Details");
            mainFrame.setContentPane(getMainForm().$$$getRootComponent$$$());
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setJMenuBar(constructMenu());
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });

        // message producer
        StatusBarService.setMessageQueue(statusBarMessageQueue);

        // message consumer
        Thread consumer = new Thread(new StatusBarMessageConsumer(statusBarMessageQueue));
        consumer.start();
    }

    private JMenuBar constructMenu() {
        JMenuBar menuBar = new JMenuBar();
        final DataController dataController = new DataController();

        // File fileMenu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setActionCommand(ActionCommand.NEW_FILE.name());
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setActionCommand(ActionCommand.OPEN_FILE.name());
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setActionCommand(ActionCommand.SAVE_FILE.name());
        JMenuItem exportMenuItem = new JMenuItem("Export...");
        exportMenuItem.setActionCommand(ActionCommand.EXPORT_FILE.name());
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand(ActionCommand.EXIT.name());

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exportMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        setActionListenerForAllItems(fileMenu, new FileOperationController());

        // Sections menu
        JMenu sectionsMenu = new JMenu("Sections");
        JMenuItem addSectionItem = new JMenuItem("Add new section...");
        addSectionItem.setActionCommand(ActionCommand.NEW_SECTION.name());
        JMenuItem editSection = new JMenuItem("Edit section...");
        editSection.setActionCommand(ActionCommand.EDIT_SECTION.name());
        JMenuItem removeSectionItem = new JMenuItem("Remove section...");
        removeSectionItem.setActionCommand(ActionCommand.DELETE_SECTION.name());

        sectionsMenu.add(addSectionItem);
        sectionsMenu.add(editSection);
        sectionsMenu.add(removeSectionItem);
        setActionListenerForAllItems(sectionsMenu, dataController);

        // Credentials menu
        JMenu credentialsMenu = new JMenu("Credentials");
        JMenuItem addCredentialItem = new JMenuItem("Add new...");
        addCredentialItem.setActionCommand(ActionCommand.NEW_CREDENTIAL.name());
        JMenuItem removeCredentialItem = new JMenuItem("Remove selected");
        removeCredentialItem.setActionCommand(ActionCommand.REMOVE_CREDENTIAL.name());
        JMenuItem editCredentialItem = new JMenuItem("Edit selected...");
        editCredentialItem.setActionCommand(ActionCommand.EDIT_CREDENTIAL.name());

        credentialsMenu.add(addCredentialItem);
        credentialsMenu.add(editCredentialItem);
        credentialsMenu.add(removeCredentialItem);
        setActionListenerForAllItems(credentialsMenu, dataController);


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
