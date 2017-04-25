package CredentialDetails.data;

import CredentialDetails.view.MainFormRender;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Model class for application
 */
public class ApplicationModel {
    private MainFormRender render;
    private Map<String, Collection<TableContentVo>> tableData = Collections.emptyMap();

    public ApplicationModel(MainFormRender render) {
        this.render = render;
    }

    /**
     * Refresh all data on the main form
     */
    public void refreshAll() {
        render.renderSectionsList(tableData.keySet());
        render.renderTable(tableData.get("Internet"));
    }

    public Map<String, Collection<TableContentVo>> getTableData() {
        return tableData;
    }

    public void setTableData(Map<String, Collection<TableContentVo>> tableData) {
        this.tableData = tableData;
    }
}
