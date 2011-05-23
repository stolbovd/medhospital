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

    private String aggregateType;
    private Constructor<T> constructor;


    /**
     * Initialize the AggregateFactory for creating instances of the given <code>aggregateType</code>.
     *
     * @param aggregateType The type of aggregate this factory creates instances of.
     * @throws IncompatibleAggregateException if the aggregate constructor throws an exception, or if the JVM security
     *                                        settings prevent the GenericEventSourcingRepository from calling the
     *                                        constructor.
     */
    public AttributeValueGenericFactory(Class<T> aggregateType) {
        super(aggregateType);
        Assert.isTrue(EventSourcedAggregateRoot.class.isAssignableFrom(aggregateType),
                "The given aggregateType must be a subtype of EventSourcedAggregateRoot");
        this.aggregateType = aggregateType.getSimpleName();
        try {
            this.constructor = aggregateType.getDeclaredConstructor(AggregateIdentifier.class);
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
        } catch (NoSuchMethodException e) {
            throw new IncompatibleAggregateException(String.format(
                    "The aggregate [%s] does not have a suitable constructor. "
                            + "See Javadoc of GenericEventSourcingRepository for more information.",
                    aggregateType.getSimpleName()), e);
        }
    }


    @SuppressWarnings({"unchecked"})
    @Override
    public T createAggregate(AggregateIdentifier aggregateIdentifier, DomainEvent firstEvent) {
        T aggregate;
        if (AggregateSnapshot.class.isInstance(firstEvent)) {
            aggregate = (T) ((AggregateSnapshot) firstEvent).getAggregate();
        } else {
            try {
                aggregate = constructor.newInstance(aggregateIdentifier);
            } catch (Exception e) {
                throw new IncompatibleAggregateException(String.format(
                        "The constructor [%s] threw an exception", constructor.toString()), e);
            }
        }
        return aggregate;
    }

    @Override
    public String getTypeIdentifier() {
        return aggregateType;
    }
}
