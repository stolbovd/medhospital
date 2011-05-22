package ua.com.alus.medhosp.backend.axon.api.patient;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import ua.com.alus.medhosp.backend.axon.api.patient.event.RemovePatientEvent;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SavePatientEvent;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 11:49
 */
public class PatientAggregate extends AbstractAnnotatedAggregateRoot {

    public PatientAggregate(AggregateIdentifier identifier) {
        super(identifier);
    }

    public PatientAggregate(String entityId, String messageId) {
        super(new StringAggregateIdentifier(entityId));
    }

    public void remove(String messageId) {
        apply(new RemovePatientEvent(getIdentifier().asString(), messageId));
    }

    public void save(String messageId) {
        apply(new SavePatientEvent(getIdentifier().asString(), messageId));
    }
}
