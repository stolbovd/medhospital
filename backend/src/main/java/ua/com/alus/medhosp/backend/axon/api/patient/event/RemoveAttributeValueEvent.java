package ua.com.alus.medhosp.backend.axon.api.patient.event;


import ua.com.alus.medhosp.prototype.cassandra.goals.DtoGoal;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 12:14
 */
public class RemoveAttributeValueEvent extends AttributeValueEvent {

    public RemoveAttributeValueEvent(String entityId, String messageId) {
        super(entityId, messageId);
    }

    @Override
    public DtoGoal getGoal() {
        return DtoGoal.REMOVE;
    }

    public RemoveAttributeValueEvent(String entityId, String messageId, String attributeId) {
        super(entityId, messageId);
        setAttributeId(attributeId);
    }
}
