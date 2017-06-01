package CredentialDetails.controller;

import CredentialDetails.app.ActionCommand;
import CredentialDetails.app.Application;
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
                ApplicationModel applicationModel = application.getMainForm().getModel();
                String activeSection = applicationModel.getActiveSection();
                if (activeSection != null && !activeSection.isEmpty()) {
                    CredentialsCUDDialog dialog = new CredentialsCUDDialog(application.getMainFrame(), false);
                    dialog.showDialog();
                } else {
                    UserMessageService.displayWarningMessage("Choose a section where you'd like to proceed");
                }

                break;
            case REMOVE_CREDENTIAL:
                onDeleteCredential(application);
                break;
            case EDIT_CREDENTIAL:
                long id = getSelectedRowId(application.getMainForm().getMainTable());
                CredentialsCUDDialog dialog = new CredentialsCUDDialog(application.getMainFrame(), true, id);
                dialog.showDialog();
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
        int result = JOptionPane.showConfirmDialog(application.getMainFrame(),
                "Are you really want to delete whole section [" + activeSection + "]?\n" +
                        "All data under it will also be deleted.",
                "Delete section",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            ApplicationModel appModel = application.getMainForm().getModel();
            appModel.deleteActiveSection();
        }
    }

    private void onDeleteCredential(Application application) {
        long rowId = getSelectedRowId(application.getMainForm().getMainTable());

        int result = JOptionPane.showConfirmDialog(application.getMainFrame(),
                "Do you want to delete selected credential's entry?",
                "Delete credentials entry",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            application.getMainForm().getModel().deleteTableRow(rowId);
        }
    }

    private long getSelectedRowId(JTable table) {
        int rowIndex = table.getSelectedRow();
        // get column index on view (may not be equals to index in the model)
        int columnIndex = table.getColumnModel().getColumnIndex("ID");
        String idStrValue = table.getValueAt(rowIndex, columnIndex).toString();

        long result = -1;
        if (idStrValue != null && !idStrValue.isEmpty()) {
            result = Long.parseLong(idStrValue);
        }

        return result;
    }
}
