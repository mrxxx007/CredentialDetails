package CredentialDetails.controller;

import CredentialDetails.app.ActionCommand;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Admin on 01.05.2017.
 */
public class DataController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ActionCommand actionCommand = ActionCommand.fromString(e.getActionCommand());

        switch (actionCommand) {
            case NEW_CREDENTIAL:
                break;
            case REMOVE_CREDENTIAL:
                break;
            case UNKNOWN:
                break;
        }
    }
}
