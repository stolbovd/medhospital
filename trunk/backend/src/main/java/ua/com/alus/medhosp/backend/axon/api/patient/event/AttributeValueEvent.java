package ua.com.alus.medhosp.backend.axon.api.patient.event;

import lombok.Getter;
import lombok.Setter;
import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityEvent;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 16:52
 */
public abstract class AttributeValueEvent extends AbstractEntityEvent {
    @Setter @Getter
    private String attributeId;

    public AttributeValueEvent(String entityId, String messageId, String userId) {
        super(entityId, messageId, userId);
    }
}
