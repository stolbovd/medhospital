package ua.com.alus.medhosp.backend.axon.api.eventsources;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.DomainEvent;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAttributeValueAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.event.AttributeValueEvent;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 17:50
 */
public class PatientAttributeValueEventSource extends AttributeValueEventSource<PatientAttributeValueAggregate> {

    public PatientAttributeValueEventSource() {
        super(PatientAttributeValueAggregate.class);
    }

    public PatientAttributeValueEventSource(Class<PatientAttributeValueAggregate> aggregateType) {
        super(aggregateType);
    }

    /**
     * This event source was created only for this overriding : fill up EventSourcedAggregateRoot
     * with values from firstEvent. Just for do not write many code for filling this root in command handlers.
     * @param aggregateIdentifier aggregateIdentifier
     * @param firstEvent firstEvent
     * @return EventSourcedAggregateRoot
     */
    @Override
    protected EventSourcedAggregateRoot instantiateAggregate(AggregateIdentifier aggregateIdentifier, DomainEvent firstEvent) {
        PatientAttributeValueAggregate root = aggregateFactory.createAggregate(aggregateIdentifier, firstEvent);
        if (firstEvent instanceof AttributeValueEvent) {
            AttributeValueEvent attributeValueEvent = (AttributeValueEvent) firstEvent;
            root.setEntityId(attributeValueEvent.getEntityId());
            root.setAttributeId(attributeValueEvent.getAttributeId());
        }
        return root;
    }
}
