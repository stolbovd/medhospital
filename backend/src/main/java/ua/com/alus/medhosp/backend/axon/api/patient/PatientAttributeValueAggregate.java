package ua.com.alus.medhosp.backend.axon.api.patient;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 11:11
 */
public class PatientAttributeValueAggregate extends AbstractAnnotatedAggregateRoot {

    public PatientAttributeValueAggregate(AggregateIdentifier identifier) {
        super(identifier);
    }

    public PatientAttributeValueAggregate(String entityId) {
        super(new StringAggregateIdentifier(entityId));
    }
}
