package CredentialDetails.controller;

import CredentialDetails.app.ActionCommand;
import CredentialDetails.app.Application;
import CredentialDetails.data.ApplicationData;
import CredentialDetails.data.ApplicationModel;
import CredentialDetails.service.FileService;
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
        ApplicationModel applicationModel = Application.getInstance().getMainForm().getModel();

        switch (actionCommand) {
            case NEW_FILE:
                UserMessageService.displayErrorMessage("Not yet implemented");
                break;
            case OPEN_FILE:
                ApplicationData data = FileService.loadFromFile();

                applicationModel.setApplicationData(data);
                applicationModel.refreshAll();
                break;
            case SAVE_FILE:
                FileService.saveApplicationDataToFile();
                break;
            case UNKNOWN:
            default:
                UserMessageService.displayWarningMessage(
                        "[FileOperationController] Unknown command received: " + e.getActionCommand());
        }
    }
}
