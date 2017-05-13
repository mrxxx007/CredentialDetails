package CredentialDetails.data;

import CredentialDetails.view.SectionsCUDDialogRender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Admin on 08.05.2017.
 */
public class SectionsCUDDialogModel {
    private SectionsCUDDialogRender render;

    private ArrayList<String> columns = new ArrayList<>();
    private int selectedColumnIndex = -1;

    public SectionsCUDDialogModel(SectionsCUDDialogRender render) {
        this.render = render;
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public void addColumn(String name) {
        columns.add(name);
        render.refreshColumnsList();
    }

    public void addColumns(Collection<String> columns) {
        this.columns.addAll(columns);
        render.refreshColumnsList();
    }

    public void removeSelectedColumn() {
        if (selectedColumnIndex > -1 && selectedColumnIndex <= columns.size()) {
            columns.remove(selectedColumnIndex);
            selectedColumnIndex--;

            // do not allow to select the first item
            if (selectedColumnIndex == 0) {
                selectedColumnIndex--;
            }
            render.refreshColumnsList();
        }
    }

    public void moveColumn(int idxFrom, int idxTo, ArrayList<String> list) {
        Collections.swap(list, idxFrom, idxTo);
        selectedColumnIndex = idxTo;
        render.refreshColumnsList();
    }

    public int getSelectedColumnIndex() {
        return selectedColumnIndex;
    }

    public void setSelectedColumnIndex(int selectedColumnIndex) {
        this.selectedColumnIndex = selectedColumnIndex;
    }
}
