package CredentialDetails.controller;

import CredentialDetails.app.ActionCommand;
import CredentialDetails.data.SectionsCUDDialogModel;
import CredentialDetails.service.UserMessageService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Admin on 08.05.2017.
 */
public class SectionsCUDDialogController implements ActionListener {
    private SectionsCUDDialogModel model;

    public SectionsCUDDialogController(SectionsCUDDialogModel model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ActionCommand actionCommand = ActionCommand.fromString(e.getActionCommand());

        switch (actionCommand) {
            case SECTIONS_DLG_ADD_COLUMN:
                String result = JOptionPane.showInputDialog(null, "Enter column name:");
                if (result != null && !result.isEmpty()) {
                    model.addColumn(result);
                }
                break;
            case SECTIONS_DLG_REMOVE_COLUMN:
                model.removeSelectedColumn();
                break;
            case SECTIONS_DLG_MOVE_DOWN:
                int selectedColumnIndex = model.getSelectedColumnIndex();
                if (selectedColumnIndex > 0 && selectedColumnIndex < model.getColumns().size() - 1) {
                    model.moveColumn(selectedColumnIndex, selectedColumnIndex + 1, model.getColumns());
                }
                break;
            case SECTIONS_DLG_MOVE_UP:
                int selectedIndex = model.getSelectedColumnIndex();
                if (selectedIndex > 1) {
                    model.moveColumn(selectedIndex, selectedIndex - 1, model.getColumns());
                }
                break;
            default:
                UserMessageService.displayWarningMessage(
                        "[SectionsCEDDialogController] Unknown action command: " + e.getActionCommand());
        }
    }
}
