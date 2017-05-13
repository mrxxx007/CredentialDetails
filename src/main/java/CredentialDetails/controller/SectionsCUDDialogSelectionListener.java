package CredentialDetails.controller;

import CredentialDetails.data.SectionsCUDDialogModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by Admin on 09.05.2017.
 */
public class SectionsCUDDialogSelectionListener implements ListSelectionListener {
    private SectionsCUDDialogModel model;

    public SectionsCUDDialogSelectionListener(SectionsCUDDialogModel model) {
        this.model = model;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList) e.getSource();
        if (e.getValueIsAdjusting()) {
            int index = list.getSelectedIndex() != 0
                    ? list.getSelectedIndex()
                    : -1;
            model.setSelectedColumnIndex(index);
        }
    }
}
