package ua.com.alus.medhosp.backend.axon.api.patient;

import lombok.Getter;
import lombok.Setter;
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
    @Setter
    @Getter
    private String attributeId;
    @Setter
    @Getter
    private String attributeValue;
    @Setter
    @Getter
    private String entityId;
    @Setter
    @Getter
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
        RemoveAttributeValueEvent event = new RemoveAttributeValueEvent(entityId, messageId, userId, attributeId);
        apply(event);
    }

    public static StringAggregateIdentifier generateIdentifier(String entityId, String attributeId) {
        return new StringAggregateIdentifier(String.format(AGGREGATE_IDENT_TEMPL, entityId, attributeId));
    }
}
