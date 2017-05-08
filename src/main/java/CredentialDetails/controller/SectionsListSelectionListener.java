package CredentialDetails.controller;

import CredentialDetails.app.Application;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by Admin on 01.05.2017.
 */
public class SectionsListSelectionListener implements ListSelectionListener {
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            //take into account only the first event
            JList sectionsList = (JList) e.getSource();
            int index = sectionsList.getSelectedIndex();
            Object selectedItem;
            if (index != -1) {
                selectedItem = sectionsList.getModel().getElementAt(index);
            } else {
                selectedItem = "";
            }

            Application.getInstance().getMainForm().getModel().setActiveSection(selectedItem.toString());
        }
    }
}
