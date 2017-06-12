package CredentialDetails.controller;

import CredentialDetails.app.ActionCommand;
import CredentialDetails.app.AppConstants;
import CredentialDetails.app.Application;
import CredentialDetails.data.ApplicationData;
import CredentialDetails.data.ApplicationModel;
import CredentialDetails.forms.NewFileDialog;
import CredentialDetails.service.FileService;
import CredentialDetails.service.StatusBarService;
import CredentialDetails.service.UserMessageService;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Admin on 23.04.2017.
 */
public class FileOperationController implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        ActionCommand actionCommand = ActionCommand.fromString(e.getActionCommand());
        Application application = Application.getInstance();
        ApplicationModel applicationModel = application.getMainForm().getModel();

        switch (actionCommand) {
            case NEW_FILE:
                NewFileDialog newFileDialog = new NewFileDialog(application.getMainFrame());
                newFileDialog.showDialog();
                break;
            case OPEN_FILE:
                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setMultiSelectionEnabled(false);
                FileFilter fileFilter =
                        new FileNameExtensionFilter("Encrypted database file", AppConstants.FILE_EXTENSION);
                fileChooser.setFileFilter(fileFilter);

                int dialogResult = fileChooser.showOpenDialog(application.getMainFrame());
                if (dialogResult == JFileChooser.APPROVE_OPTION) {
                    File result = fileChooser.getSelectedFile();
                    ApplicationData data = FileService.loadFromFile(result);

                    if (data != null) {
                        applicationModel.setApplicationData(data);
                        applicationModel.setActiveSection("");
                        applicationModel.setCurrentFile(result);
                        applicationModel.refreshAll();
                    }
                }
                StatusBarService.displayMessage("Load completed");
                break;
            case SAVE_FILE:
                FileService.saveApplicationDataToFile();
                StatusBarService.displayMessage("Saved successfully");
                break;
            case UNKNOWN:
            default:
                UserMessageService.displayWarningMessage(
                        "[FileOperationController] Unknown command received: " + e.getActionCommand());
        }
    }
}
