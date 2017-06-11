package CredentialDetails.view;

import CredentialDetails.app.Application;
import CredentialDetails.data.ApplicationModel;
import CredentialDetails.data.TableRowVo;
import CredentialDetails.forms.MainForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MainFormRender {
    private MainForm mainForm;

    public MainFormRender(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    public void renderSectionsList(Collection<String> items, String selectedItem) {
        JList sectionsList = mainForm.getSectionsList();
        sectionsList.setListData(items.toArray());
        sectionsList.setSelectedValue(selectedItem, true);
    }

    public void renderTable(Collection<TableRowVo> sectionData) {
        DefaultTableModel tableModel = (DefaultTableModel) mainForm.getMainTable().getModel();
        if (sectionData != null) {

            ApplicationModel applicationModel = Application.getInstance().getMainForm().getModel();
            String activeSection = applicationModel.getActiveSection();
            Map<String, List<String>> sectionColumns = applicationModel.getApplicationData().getSectionColumns();
            tableModel.setColumnIdentifiers(sectionColumns.get(activeSection).toArray());
            tableModel.setRowCount(0);

            for (TableRowVo tableRowVo : sectionData) {
                Map<String, String> data = tableRowVo.getData();

                int columnCount = tableModel.getColumnCount();
                Object[] tableRow = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {

                    String columnName = tableModel.getColumnName(i);
                    tableRow[i] = data.get(columnName);
                }
                tableModel.addRow(tableRow);
            }
        } else {
            // clear table
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
        }
    }
}
