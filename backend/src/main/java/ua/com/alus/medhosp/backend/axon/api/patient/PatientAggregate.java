package ua.com.alus.medhosp.backend.axon.api.patient;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import ua.com.alus.medhosp.backend.axon.api.patient.event.RemovePatientEvent;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SavePatientEvent;

import java.io.Serializable;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 11:49
 */
public class PatientAggregate extends AbstractAnnotatedAggregateRoot implements Serializable {

    public PatientAggregate(AggregateIdentifier identifier) {
        super(identifier);
    }

    public PatientAggregate(String entityId) {
        super(new StringAggregateIdentifier(entityId));
    }

    public void remove(String messageId, String userId) {
        apply(new RemovePatientEvent(getIdentifier().asString(), messageId, userId));
    }

    public void save(String messageId, String userId) {
        apply(new SavePatientEvent(getIdentifier().asString(), messageId, userId));
    }
}
