package CredentialDetails.controller;

import CredentialDetails.app.ActionCommand;
import CredentialDetails.app.Application;
import CredentialDetails.data.ApplicationModel;
import CredentialDetails.data.TableContentVo;
import CredentialDetails.service.FileService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Admin on 23.04.2017.
 */
public class FileOperationController implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        ActionCommand actionCommand = ActionCommand.fromString(e.getActionCommand());
        ApplicationModel applicationModel = Application.getInstance().getModel();

        switch (actionCommand) {
            case OPEN_FILE:
                Map<String, Collection<TableContentVo>> data = FileService.loadFromFile();

                applicationModel.setTableData(data);
                applicationModel.refreshAll();
                break;
            case SAVE_FILE:
                FileService.saveToFile(applicationModel.getTableData());
                break;
            case UNKNOWN:
            default:
                System.out.println("Unknown command received: " + e.getActionCommand());
        }
    }
}
