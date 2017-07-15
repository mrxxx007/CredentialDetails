package CredentialDetails.controller;

import CredentialDetails.app.ActionCommand;
import CredentialDetails.app.Application;
import CredentialDetails.data.ApplicationModel;
import CredentialDetails.forms.NewFileDialog;
import CredentialDetails.forms.OpenFileDialog;
import CredentialDetails.service.FileService;
import CredentialDetails.service.StatusBarService;
import CredentialDetails.service.UserMessageService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                OpenFileDialog openFileDialog = new OpenFileDialog(application.getMainFrame());
                int dialogResult = openFileDialog.showDialog();

                if (dialogResult == OpenFileDialog.RESULT_OK) {
                    StatusBarService.displayMessage("Load completed");
                } else if (dialogResult == OpenFileDialog.RESULT_ERROR) {
                    StatusBarService.displayMessage("Could not load from file");
                }

                break;
            case SAVE_FILE:
                FileService.saveApplicationDataToFile(applicationModel.getMasterKey());
                break;
            case UNKNOWN:
            default:
                UserMessageService.displayWarningMessage(
                        "[FileOperationController] Unknown command received: " + e.getActionCommand());
        }
    }
}
