package ua.com.alus.medhosp.backend.axon.api.patient;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SaveAttributeEvent;

import java.io.Serializable;

/**
 * Created Usatov Alexey
 * Date: 24.05.11
 * Time: 14:28
 */
public class PatientAttributeAggregate extends AbstractAnnotatedAggregateRoot implements Serializable {

    private String entityId;
    private String type;
    private String name;
    private String valueType;
    private String userId;

    public PatientAttributeAggregate(AggregateIdentifier identifier) {
        super(identifier);
    }

    public PatientAttributeAggregate(String entityId, String name) {
        super(new StringAggregateIdentifier(entityId));
        this.entityId = entityId;
        this.name = name;
    }

    public void save(String messageId, String userId) {
        SaveAttributeEvent event = new SaveAttributeEvent(getIdentifier().asString(), messageId, userId, name);
        event.setType(type);
        event.setValueType(valueType);
        apply(event);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
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
