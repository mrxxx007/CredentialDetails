package CredentialDetails.app;

/**
 * Created by Admin on 29.04.2017.
 */
public enum ActionCommand {
    NEW_FILE,
    OPEN_FILE,
    SAVE_FILE,
    EXIT,

    NEW_CREDENTIAL,
    REMOVE_CREDENTIAL,

    NEW_SECTION,
    EDIT_SECTION,
    DELETE_SECTION,

    UNKNOWN;

    public static ActionCommand fromString(String value) {
        for (ActionCommand command : ActionCommand.values()) {
            if (command.name().equals(value)) {
                return command;
            }
        }
        return UNKNOWN;
    }
}
