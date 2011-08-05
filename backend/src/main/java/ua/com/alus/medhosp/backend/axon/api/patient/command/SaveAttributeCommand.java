package ua.com.alus.medhosp.backend.axon.api.patient.command;

import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityCommand;

/**
 * Created Usatov Alexey
 * Date: 24.05.11
 * Time: 14:24
 */
public class SaveAttributeCommand extends AbstractEntityCommand {
    private String type;
    private String name;
    private String definition;

    public SaveAttributeCommand() {

    }

    public SaveAttributeCommand(String entityId, String messageId, String userId, String name) {
        super(entityId, messageId, userId);
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
