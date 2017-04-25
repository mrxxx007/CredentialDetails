package CredentialDetails.controller;

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
public class FileOperationButtonController implements ActionListener{
    public void actionPerformed(ActionEvent e) {
        ApplicationModel applicationModel = Application.getInstance().getModel();
        if (e.getSource() == Application.getInstance().getLoadButton()) {
            Map<String, Collection<TableContentVo>> data = FileService.loadFromFile();

            applicationModel.setTableData(data);
            applicationModel.refreshAll();
        } else if (e.getSource() == Application.getInstance().getSaveButton()) {
            FileService.saveToFile(applicationModel.getTableData());
        }

    }
}
