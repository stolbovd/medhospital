package ua.com.alus.medhosp.backend.axon.api.patient.event;

import ua.com.alus.medhosp.prototype.cassandra.goals.DtoGoals;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 12:14
 */
public class RemoveAttributeValueEvent extends AttributeValueEvent {

    public static final String GOAL = DtoGoals.REMOVE.name();

    public RemoveAttributeValueEvent(String entityId, String messageId) {
        super(entityId, messageId);
    }

    public RemoveAttributeValueEvent(String entityId, String messageId, String attributeId) {
        super(entityId, messageId);
        setAttributeId(attributeId);
    }
}
