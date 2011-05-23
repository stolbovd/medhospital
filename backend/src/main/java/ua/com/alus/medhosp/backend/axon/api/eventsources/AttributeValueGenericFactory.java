package ua.com.alus.medhosp.backend.axon.api.eventsources;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.DomainEvent;
import org.axonframework.eventsourcing.AggregateSnapshot;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.eventsourcing.GenericAggregateFactory;
import org.axonframework.eventsourcing.IncompatibleAggregateException;
import org.axonframework.util.Assert;

import java.lang.reflect.Constructor;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 17:29
 */
public class AttributeValueGenericFactory<T extends EventSourcedAggregateRoot> extends GenericAggregateFactory<T> {

    public AttributeValueGenericFactory(Class<T> aggregateType) {
        super(aggregateType);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public T createAggregate(AggregateIdentifier aggregateIdentifier, DomainEvent firstEvent) {
        return super.createAggregate(aggregateIdentifier, firstEvent);
    }

    @Override
    public String getTypeIdentifier() {
        return super.getTypeIdentifier();
    }
}
