package ua.com.alus.medhosp.backend.axon.api.patient.event;


import ua.com.alus.medhosp.prototype.cassandra.goals.DtoGoal;

import java.io.Serializable;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 11:50
 */
public class SaveAttributeValueEvent extends AttributeValueEvent implements Serializable {

    private String attributeValue;

    public SaveAttributeValueEvent(String entityId, String messageId, String userId) {
        super(entityId, messageId, userId);
    }

    public SaveAttributeValueEvent(String entityId, String messageId, String userId, String attributeId, String attributeValue) {
        super(entityId, messageId, userId);
        setAttributeId(attributeId);
        this.attributeValue = attributeValue;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    @Override
    public DtoGoal getGoal() {
        return DtoGoal.SAVE;
    }
}
