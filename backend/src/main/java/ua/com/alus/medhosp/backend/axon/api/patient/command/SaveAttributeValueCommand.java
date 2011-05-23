package ua.com.alus.medhosp.backend.axon.api.patient.command;

import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityCommand;

/**
 * Created by Usatov Alexey.
 * Date: 23.05.11
 * Time: 11:07
 */
public class SaveAttributeValueCommand extends AbstractEntityCommand {

    private String attributeId;

    private String attributeValue;

    public SaveAttributeValueCommand() {

    }

    public SaveAttributeValueCommand(String entityId, String messageId, String attributeId, String attributeValue) {
        super(entityId, messageId);
        this.attributeId = attributeId;
        this.attributeValue = attributeValue;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}
