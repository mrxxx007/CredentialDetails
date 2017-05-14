package CredentialDetails.controller;

import CredentialDetails.app.ActionCommand;
import CredentialDetails.app.Application;
import CredentialDetails.data.ApplicationData;
import CredentialDetails.data.ApplicationModel;
import CredentialDetails.data.SectionsCUDDialogModel;
import CredentialDetails.data.TableRowVo;
import CredentialDetails.forms.SectionsCUDDialog;
import CredentialDetails.service.UserMessageService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                //FIXME: NPE in case when table is empty (applicationModel.getApplicationData().getSectionColumns())
                ApplicationModel applicationModel = Application.getInstance().getMainForm().getModel();

                long rowId = applicationModel.getNextTableId();

                //TODO: temporary solution. Will be replaced by dialog window
                String activeSection = applicationModel.getActiveSection();
                Map<String, String> newRowData = new HashMap<>();
                for (String columnName : applicationModel.getApplicationData().getSectionColumns().get(activeSection)) {
                    if (columnName.equals("ID")) {
                        newRowData.put(columnName, Long.toString(rowId));
                    } else {
                        newRowData.put(columnName, "value for " + columnName);
                    }
                }

                TableRowVo tableRowData = new TableRowVo();
                tableRowData.setId(rowId);
                tableRowData.setSectionName(activeSection);
                tableRowData.setData(newRowData);
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
