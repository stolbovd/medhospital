package ua.com.alus.medhosp.backend.axon.api.patient.eventsource;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.DomainEvent;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import ua.com.alus.medhosp.backend.axon.api.eventsources.AttributeValueEventSource;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAttributeAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.event.AttributeEvent;

/**
 * Created Usatov Alexey
 * Date: 24.05.11
 * Time: 14:42
 */
public class PatientAttributeEventSource extends AttributeValueEventSource<PatientAttributeAggregate> {
    public PatientAttributeEventSource() {
        super(PatientAttributeAggregate.class);
    }

    public PatientAttributeEventSource(Class<PatientAttributeAggregate> aggregateType) {
        super(aggregateType);
    }

    /**
     * This event source was created only for this overriding : fill up EventSourcedAggregateRoot
     * with values from firstEvent. Just for do not write many code for filling this root in command handlers.
     *
     * @param aggregateIdentifier aggregateIdentifier
     * @param firstEvent          firstEvent
     * @return EventSourcedAggregateRoot
     */
    @Override
    protected EventSourcedAggregateRoot instantiateAggregate(AggregateIdentifier aggregateIdentifier, DomainEvent firstEvent) {
        PatientAttributeAggregate root = aggregateFactory.createAggregate(aggregateIdentifier, firstEvent);
        if (firstEvent instanceof AttributeEvent) {
            AttributeEvent attributeValueEvent = (AttributeEvent) firstEvent;
            root.setEntityId(attributeValueEvent.getEntityId());
            root.setName(attributeValueEvent.getName());
            root.setType(attributeValueEvent.getType());
            root.setValueType(attributeValueEvent.getValueType());
            root.setUserId(attributeValueEvent.getUserId());
        }
        return root;
    }
}
