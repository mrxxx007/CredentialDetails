package CredentialDetails.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A serializable object to store application data in a file
 */
public class ApplicationData implements Serializable {
    static final long serialVersionUID = 1L;

    private long maxTableId;
    /** Key - section name; Value - set of columns */
    private Map<String, Set<String>> sectionColumns = new HashMap<>();
    /** Key - section name; Value - section data */
    private Map<String, Collection<TableRowVo>> tableData = new HashMap<>();

    public long getMaxTableId() {
        return maxTableId;
    }

    public void setMaxTableId(long maxTableId) {
        this.maxTableId = maxTableId;
    }

    public Map<String, Collection<TableRowVo>> getTableData() {
        return tableData;
    }

    public void setTableData(Map<String, Collection<TableRowVo>> tableData) {
        this.tableData = tableData;
    }

    public Map<String, Set<String>> getSectionColumns() {
        return sectionColumns;
    }

    public void setSectionColumns(Map<String, Set<String>> sectionColumns) {
        this.sectionColumns = sectionColumns;
    }
}
