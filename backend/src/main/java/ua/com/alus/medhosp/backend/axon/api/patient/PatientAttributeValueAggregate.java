package ua.com.alus.medhosp.backend.axon.api.patient;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SaveAttributeValueEvent;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 11:11
 */
public class PatientAttributeValueAggregate extends AbstractAnnotatedAggregateRoot {

    private String attributeId;
    private String attributeValue;


    public PatientAttributeValueAggregate(AggregateIdentifier identifier) {
        super(identifier);
    }

    public PatientAttributeValueAggregate(String entityId) {
        super(new StringAggregateIdentifier(entityId));
    }

    public PatientAttributeValueAggregate(String entityId, String attributeId, String attributeValue) {
        super(new StringAggregateIdentifier(entityId));
        this.attributeId = attributeId;
        this.attributeValue = attributeValue;
    }

    public void save(String messageId) {
        SaveAttributeValueEvent event = new SaveAttributeValueEvent(getIdentifier().asString(), messageId,
                attributeId, attributeValue);
        apply(event);
    }
}
