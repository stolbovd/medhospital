package ua.com.alus.medhosp.backend.axon.api.patient;

import lombok.Getter;
import lombok.Setter;
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

    @Setter
    @Getter
    private String entityId;
    @Setter
    @Getter
    private String type;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String definition;
    @Setter
    @Getter
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
        event.setDefinition(definition);
        apply(event);
    }
}
