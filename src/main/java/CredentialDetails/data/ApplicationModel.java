package CredentialDetails.data;

import CredentialDetails.view.MainFormRender;

import java.util.Collection;
import java.util.Iterator;
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
        render.renderSectionsList(applicationData.getTableData().keySet(), activeSection);
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

    public void deleteActiveSection() {
        ApplicationData applicationData = this.applicationData;
        applicationData.getSectionColumns().remove(activeSection);
        applicationData.getTableData().remove(activeSection);
        refreshAll();
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

    public void updateTableRowData(TableRowVo updatedRow) {
        long id = updatedRow.getId();

        TableRowVo tableRow = findRowById(id);
        if (tableRow != null) {
            tableRow.setData(updatedRow.getData());
            refreshTable();
        }
    }

    public void deleteTableRow(long rowId) {
        Collection<TableRowVo> tableRows = this.applicationData.getTableData().get(activeSection);
        Iterator<TableRowVo> iterator = tableRows.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == rowId) {
                iterator.remove();
                refreshTable();
                break;
            }
        }
    }

    private TableRowVo findRowById(long id) {
        TableRowVo result = null;
        Collection<TableRowVo> tableRows = this.applicationData.getTableData().get(activeSection);
        for (TableRowVo tableRow : tableRows) {
            if (tableRow.getId() == id) {
                result = tableRow;
                break;
            }
        }

        return result;
    }
}
