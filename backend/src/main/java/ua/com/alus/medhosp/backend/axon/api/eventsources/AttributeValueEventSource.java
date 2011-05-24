package ua.com.alus.medhosp.backend.axon.api.eventsources;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.DomainEvent;
import org.axonframework.eventsourcing.EventSourcedAggregateRoot;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.repository.LockingStrategy;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 17:22
 */
public class AttributeValueEventSource<T extends EventSourcedAggregateRoot> extends EventSourcingRepository {

    protected AttributeGenericFactory<T> aggregateFactory;

    public AttributeValueEventSource(Class<T> aggregateType) {
        this(aggregateType, LockingStrategy.PESSIMISTIC);
    }


    public AttributeValueEventSource(Class<T> aggregateType, LockingStrategy lockingStrategy) {
        super(lockingStrategy);
        aggregateFactory = new AttributeGenericFactory<T>(aggregateType);
    }

    @Override
    protected EventSourcedAggregateRoot instantiateAggregate(AggregateIdentifier aggregateIdentifier, DomainEvent firstEvent) {
        return aggregateFactory.createAggregate(aggregateIdentifier, firstEvent);
    }

    @Override
    public String getTypeIdentifier() {
        return aggregateFactory.getTypeIdentifier();
    }
}
