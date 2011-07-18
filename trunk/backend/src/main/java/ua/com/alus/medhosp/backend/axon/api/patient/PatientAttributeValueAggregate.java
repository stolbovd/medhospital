package ua.com.alus.medhosp.backend.axon.api.patient;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import ua.com.alus.medhosp.backend.axon.api.patient.event.RemoveAttributeValueEvent;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SaveAttributeValueEvent;

import java.io.Serializable;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 11:11
 */
public class PatientAttributeValueAggregate extends AbstractAnnotatedAggregateRoot implements Serializable {
    public static final String AGGREGATE_IDENT_TEMPL = "%s-%s";
    private String attributeId;
    private String attributeValue;
    private String entityId;
    private String userId;

    public PatientAttributeValueAggregate(AggregateIdentifier identifier) {
        super(identifier);
    }


    public PatientAttributeValueAggregate(String entityId, String attributeId, String userId, String attributeValue) {
        super(generateIdentifier(entityId, attributeId));
        this.entityId = entityId;
        this.attributeId = attributeId;
        this.attributeValue = attributeValue;
        this.userId = userId;
    }

    public void save(String messageId) {
        SaveAttributeValueEvent event = new SaveAttributeValueEvent(entityId, messageId, userId,
                attributeId, attributeValue);
        apply(event);
    }

    public void remove(String messageId) {
        RemoveAttributeValueEvent event = new RemoveAttributeValueEvent(entityId, messageId, attributeId);
        apply(event);
    }

    public static StringAggregateIdentifier generateIdentifier(String entityId, String attributeId) {
        return new StringAggregateIdentifier(String.format(AGGREGATE_IDENT_TEMPL, entityId, attributeId));
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

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
