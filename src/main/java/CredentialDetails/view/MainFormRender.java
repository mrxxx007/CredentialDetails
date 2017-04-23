package CredentialDetails.view;

import CredentialDetails.data.TableContentVo;
import CredentialDetails.forms.MainForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Collection;

public class MainFormRender {
    private MainForm mainForm;

    public MainFormRender(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    public void renderSectionsList(Collection<String> items) {
        JList sectionsList = mainForm.getSectionsList();
        sectionsList.setListData(items.toArray());
    }

    public void renderTable(Collection<TableContentVo> tableData) {
        DefaultTableModel tableModel = (DefaultTableModel) mainForm.getMainTable().getModel();
        tableModel.setColumnIdentifiers(TableContentVo.TABLE_COLUMNS);

        for (TableContentVo data : tableData) {
            tableModel.addRow(new Object[] {
                    data.getTitle(),
                    data.getUrl(),
                    data.getLogin(),
                    data.getPassword(),
                    data.getComment() });
        }
    }
}
