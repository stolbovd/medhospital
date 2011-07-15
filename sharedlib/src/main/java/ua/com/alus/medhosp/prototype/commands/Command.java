package ua.com.alus.medhosp.prototype.commands;

/**
 * Enumeration of commands.
 *
 * Created by Usatov Alexey
 */
public enum Command {
    SAVE_PATIENT("SavePatientCommand"), SAVE_ATTRIBUTE_VALUE("SaveAttributeValueCommand"),
    SAVE_ATTRIBUTE("SaveAttributeCommand"), REMOVE_PATIENT("RemovePatientCommand"),
    REMOVE_ATTRIBUTE_VALUE("RemoveAttributeValueCommand");

    private String commandName;
    private Command(String commandName){
        this.commandName = commandName;
    }

    public String getCommandName(){
        return commandName;
    }
}
