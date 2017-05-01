package CredentialDetails.view;

import CredentialDetails.app.Application;
import CredentialDetails.data.ApplicationModel;
import CredentialDetails.data.TableRowVo;
import CredentialDetails.forms.MainForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MainFormRender {
    private MainForm mainForm;

    public MainFormRender(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    public void renderSectionsList(Collection<String> items) {
        JList sectionsList = mainForm.getSectionsList();
        sectionsList.setListData(items.toArray());
    }

    public void renderTable(Collection<TableRowVo> sectionData) {
        if (sectionData != null) {
            DefaultTableModel tableModel = (DefaultTableModel) mainForm.getMainTable().getModel();

            ApplicationModel applicationModel = Application.getInstance().getModel();
            String activeSection = applicationModel.getActiveSection();
            Map<String, Set<String>> sectionColumns = applicationModel.getApplicationData().getSectionColumns();
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

//            Collection<Map<String, String>> data = sectionData.getData();
//            for (Map<String, String> rowData : data) {
//                int columnCount = tableModel.getColumnCount();
//                Object[] tableRow = new Object[columnCount];
//                for (int i = 0; i < columnCount; i++) {
//
//                    String columnName = tableModel.getColumnName(i);
//                    tableRow[i] = rowData.get(columnName);
//                }
//                tableModel.addRow(tableRow);
//            }

//            for (TableContentVo data : tableData) {
//                tableModel.addRow(new Object[]{
//                        data.getTitle(),
//                        data.getUrl(),
//                        data.getLogin(),
//                        data.getPassword(),
//                        data.getComment()});
//            }
        }
    }
}
