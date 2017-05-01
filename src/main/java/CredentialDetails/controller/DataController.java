package CredentialDetails.controller;

import CredentialDetails.app.ActionCommand;
import CredentialDetails.app.Application;
import CredentialDetails.data.ApplicationModel;
import CredentialDetails.data.TableRowVo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 01.05.2017.
 */
public class DataController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ActionCommand actionCommand = ActionCommand.fromString(e.getActionCommand());

        switch (actionCommand) {
            case NEW_CREDENTIAL:
                ApplicationModel applicationModel = Application.getInstance().getModel();

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
                ApplicationModel applicationModel1 = Application.getInstance().getModel();

                JTable mainTable = Application.getInstance().getMainTable();
                int rowIndex = mainTable.getSelectedRow();
                //mainTable.getModel().
                break;
            case UNKNOWN:
                break;
        }
    }
}
