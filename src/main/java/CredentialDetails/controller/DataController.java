package CredentialDetails.controller;

import CredentialDetails.app.ActionCommand;
import CredentialDetails.app.Application;
import CredentialDetails.data.ApplicationData;
import CredentialDetails.data.ApplicationModel;
import CredentialDetails.data.SectionsCUDDialogModel;
import CredentialDetails.forms.CredentialsCUDDialog;
import CredentialDetails.forms.SectionsCUDDialog;
import CredentialDetails.service.UserMessageService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Admin on 01.05.2017.
 */
public class DataController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ActionCommand actionCommand = ActionCommand.fromString(e.getActionCommand());
        Application application = Application.getInstance();

        switch (actionCommand) {
            case NEW_SECTION:
                SectionsCUDDialog newSectionsCUDDialog = new SectionsCUDDialog(application.getMainFrame(), false);
                newSectionsCUDDialog.showDialog();
                break;
            case EDIT_SECTION:
                onEditSection(application);
                break;
            case DELETE_SECTION:
                onDeleteSection(application);
                break;
            case NEW_CREDENTIAL:
                ApplicationModel applicationModel = Application.getInstance().getMainForm().getModel();
                String activeSection = applicationModel.getActiveSection();
                if (activeSection != null && !activeSection.isEmpty()) {
                    CredentialsCUDDialog dialog = new CredentialsCUDDialog(application.getMainFrame(), false);
                    dialog.showDialog();
                } else {
                    UserMessageService.displayWarningMessage("Choose a section where you'd like to proceed");
                }

                break;
            case REMOVE_CREDENTIAL:
                ApplicationModel applicationModel1 = application.getMainForm().getModel();

                JTable mainTable = application.getMainForm().getMainTable();
                int rowIndex = mainTable.getSelectedRow();
                //mainTable.getModel().
                break;
            case UNKNOWN:
            default:
                UserMessageService.displayWarningMessage(
                        "[DataController] Unknown command received: " + e.getActionCommand());
                break;
        }
    }

    private void onEditSection(Application application) {
        ApplicationModel appModel = application.getMainForm().getModel();
        String activeSection = appModel.getActiveSection();
        if (activeSection != null && !activeSection.isEmpty()) {
            SectionsCUDDialog editSectionsCUDDialog = new SectionsCUDDialog(application.getMainFrame(), true);
            SectionsCUDDialogModel dialogModel = editSectionsCUDDialog.getModel();
            List<String> columns = appModel.getApplicationData().getSectionColumns().get(activeSection);

            dialogModel.addColumns(columns);

            editSectionsCUDDialog.showDialog();
        } else {
            UserMessageService.displayWarningMessage("No one section selected");
        }
    }

    private void onDeleteSection(Application application) {
        final String activeSection = application.getMainForm().getModel().getActiveSection();
        int result = JOptionPane.showConfirmDialog(Application.getInstance().getMainFrame(),
                "Are you really want to remove the section [" + activeSection + "]?",
                "Remove section",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            ApplicationModel appModel = application.getMainForm().getModel();
            ApplicationData applicationData = appModel.getApplicationData();
            applicationData.getSectionColumns().remove(activeSection);
            applicationData.getTableData().remove(activeSection);
            appModel.refreshAll();
        }
    }
}
