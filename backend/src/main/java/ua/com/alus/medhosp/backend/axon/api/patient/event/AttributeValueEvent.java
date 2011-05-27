package ua.com.alus.medhosp.backend.axon.api.patient.event;

import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityEvent;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 16:52
 */
public class AttributeValueEvent extends AbstractEntityEvent {
    private String attributeId;

    public AttributeValueEvent(String entityId, String messageId) {
        super(entityId, messageId);
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }
}