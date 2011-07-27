package ua.com.alus.medhosp.backend.axon.api.patient.snapshot;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.DomainEvent;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.AggregateSnapshot;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAttributeValueAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.event.AttributeValueEvent;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 16:34
 */
public class AttributeValueAggregateFactory implements AggregateFactory<PatientAttributeValueAggregate> {
    @Override
    @SuppressWarnings("unchecked")
    public PatientAttributeValueAggregate createAggregate(AggregateIdentifier aggregateIdentifier, DomainEvent firstEvent) {
        PatientAttributeValueAggregate aggregate = new PatientAttributeValueAggregate(firstEvent.getAggregateIdentifier());
        if (firstEvent instanceof AttributeValueEvent) {
            AttributeValueEvent event = (AttributeValueEvent) firstEvent;
            aggregate.setEntityId(event.getEntityId());
            aggregate.setPatientId(event.getPatientId());
            aggregate.setUserId(event.getUserId());
        } else if (firstEvent instanceof AggregateSnapshot) {
            return ((AggregateSnapshot<PatientAttributeValueAggregate>) firstEvent).getAggregate();
        }
        return aggregate;
    }

    @Override
    public String getTypeIdentifier() {
        return PatientAttributeValueAggregate.class.getSimpleName();
    }
}
