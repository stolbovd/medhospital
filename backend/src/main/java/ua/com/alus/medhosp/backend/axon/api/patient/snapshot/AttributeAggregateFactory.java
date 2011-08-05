package ua.com.alus.medhosp.backend.axon.api.patient.snapshot;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.DomainEvent;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.AggregateSnapshot;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAttributeAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.event.AttributeEvent;

/**
 * Created Usatov Alexey
 * Date: 24.05.11
 * Time: 14:52
 */
public class AttributeAggregateFactory implements AggregateFactory<PatientAttributeAggregate> {

    @Override
    @SuppressWarnings("unchecked")
    public PatientAttributeAggregate createAggregate(AggregateIdentifier aggregateIdentifier, DomainEvent firstEvent) {
        PatientAttributeAggregate aggregate = new PatientAttributeAggregate(firstEvent.getAggregateIdentifier());
        if (firstEvent instanceof AttributeEvent) {
            AttributeEvent event = (AttributeEvent) firstEvent;
            aggregate.setEntityId(event.getEntityId());
            aggregate.setName(event.getName());
            aggregate.setType(event.getType());
            aggregate.setDefinition(event.getDefinition());
            aggregate.setUserId(event.getUserId());
        } else if (firstEvent instanceof AggregateSnapshot) {
            return ((AggregateSnapshot<PatientAttributeAggregate>) firstEvent).getAggregate();
        }
        return aggregate;
    }

    @Override
    public String getTypeIdentifier() {
        return PatientAttributeAggregate.class.getSimpleName();
    }
}
