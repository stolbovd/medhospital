package ua.com.alus.medhosp.backend.axon.api.patient.command;

import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityCommand;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 12:13
 */
public class RemoveAttributeValueCommand extends AbstractEntityCommand {

    private String attributeId;

    public RemoveAttributeValueCommand() {

    }

    public RemoveAttributeValueCommand(String entityId, String messageId, String userId, String attributeId) {
        super(entityId, messageId, userId);
        this.attributeId = attributeId;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }
}
