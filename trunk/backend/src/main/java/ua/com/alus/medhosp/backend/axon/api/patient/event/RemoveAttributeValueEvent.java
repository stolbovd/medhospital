package ua.com.alus.medhosp.backend.axon.api.patient.event;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 12:14
 */
public class RemoveAttributeValueEvent extends AttributeValueEvent {


    public RemoveAttributeValueEvent(String entityId, String messageId) {
        super(entityId, messageId);
    }

    public RemoveAttributeValueEvent(String entityId, String messageId, String attributeId) {
        super(entityId, messageId);
        setAttributeId(attributeId);
    }
}
