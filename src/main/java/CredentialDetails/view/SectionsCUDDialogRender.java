package CredentialDetails.view;

import CredentialDetails.data.SectionsCUDDialogModel;
import CredentialDetails.forms.SectionsCUDDialog;

import java.util.ArrayList;

/**
 * Created by Admin on 08.05.2017.
 */
public class SectionsCUDDialogRender {
    private SectionsCUDDialog dialog;

    public SectionsCUDDialogRender(SectionsCUDDialog dialog) {
        this.dialog = dialog;
    }

    public void refreshColumnsList() {
        SectionsCUDDialogModel model = dialog.getModel();
        ArrayList<String> columns = model.getColumns();
        dialog.getColumnsList().setListData(columns.toArray());

        // use the latest element if index is out of bound
        if (model.getSelectedColumnIndex() > columns.size() - 1) {
            model.setSelectedColumnIndex(columns.size() - 1);
        }

        dialog.getColumnsList().setSelectedIndex(model.getSelectedColumnIndex());
    }
}
