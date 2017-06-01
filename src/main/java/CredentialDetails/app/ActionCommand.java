package CredentialDetails.app;

/**
 * Enumeration of possible commands for Action Listeners
 */
public enum ActionCommand {
    // Main window
    NEW_FILE,
    OPEN_FILE,
    SAVE_FILE,
    EXIT,

    NEW_CREDENTIAL,
    EDIT_CREDENTIAL,
    REMOVE_CREDENTIAL,

    NEW_SECTION,
    EDIT_SECTION,
    DELETE_SECTION,

    // Create/update Sections dialog window
    SECTIONS_DLG_ADD_COLUMN,
    SECTIONS_DLG_REMOVE_COLUMN,
    SECTIONS_DLG_MOVE_UP,
    SECTIONS_DLG_MOVE_DOWN,

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
