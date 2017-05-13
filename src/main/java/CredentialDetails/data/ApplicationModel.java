package CredentialDetails.data;

import CredentialDetails.view.MainFormRender;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Model class for application
 */
public class ApplicationModel {
    private MainFormRender render;

    /**
     * Key - section name; Value - section data
     */
    //private Map<String, SectionDataVo> tableData = Collections.emptyMap();
    private String activeSection = "";
    private AtomicLong maxTableId = new AtomicLong(0);
    private ApplicationData applicationData = new ApplicationData();

    /**
     * Constructor
     * @param render A render class for the model
     */
    public ApplicationModel(MainFormRender render) {
        this.render = render;
    }

    /**
     * Refresh all data on the main form
     */
    public void refreshAll() {
        refreshSectionsList();
        refreshTable();
    }

    public void refreshSectionsList() {
        render.renderSectionsList(applicationData.getTableData().keySet());
    }

    public void refreshTable() {
        render.renderTable(applicationData.getTableData().get(activeSection));
    }

    public long getNextTableId() {
        return this.maxTableId.incrementAndGet();
    }

    public long getMaxTableId() {
        return this.maxTableId.get();
    }

    public void setMaxTableId(long value) {
        this.maxTableId.set(value);
    }


    public ApplicationData getApplicationData() {
        return applicationData;
    }

    public void setApplicationData(ApplicationData applicationData) {
        this.applicationData = applicationData;
    }

    public String getActiveSection() {
        return activeSection;
    }

    public void setActiveSection(String activeSection) {
        this.activeSection = activeSection;

        refreshTable();
    }

    public void appendTableDataForActiveSection(TableRowVo tableRow) {
        if (tableRow != null) {
            if (tableRow.getId() != -1) {
                this.applicationData.getTableData().get(getActiveSection()).add(tableRow);
                refreshTable();
            } else {
                throw new IllegalArgumentException(
                        "Table row must have a correct ID. Current value = " + tableRow.getId());
            }
        }
    }
}
