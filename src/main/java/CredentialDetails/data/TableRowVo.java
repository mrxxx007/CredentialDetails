package CredentialDetails.data;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Admin on 01.05.2017.
 */
public class TableRowVo implements Serializable {
    static final long serialVersionUID = 1L;

    /** row id */
    private long id = -1;
    //TODO: may be section name is not needed here
    private String sectionName;
    /** Key - column name, Value - column data */
    private Map<String, String> data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
