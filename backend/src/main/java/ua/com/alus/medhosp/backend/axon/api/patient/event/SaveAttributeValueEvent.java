package ua.com.alus.medhosp.backend.axon.api.patient.event;

import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityEvent;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 11:50
 */
public class SaveAttributeValueEvent extends AbstractEntityEvent {
    private String attributeId;
    private String attributeValue;

    public SaveAttributeValueEvent(String entityId, String messageId) {
        super(entityId, messageId);
    }

    public SaveAttributeValueEvent(String entityId, String messageId, String attributeId, String attributeValue){
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
