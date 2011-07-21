package ua.com.alus.medhosp.backend.axon.api.message;

import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import ua.com.alus.medhosp.backend.axon.api.message.event.ReSendMessageEvent;

import java.io.Serializable;

/**
 * Message Aggregate
 * <p/>
 * Created by Usatov Alexey
 */
public class MessageAggregate extends AbstractAnnotatedAggregateRoot implements Serializable {

    public MessageAggregate(AggregateIdentifier identifier) {
        super(identifier);
    }

    public MessageAggregate(String entityId) {
        super(new StringAggregateIdentifier(entityId));
    }

    public void resendMessage(String messageId, String userId) {
        apply(new ReSendMessageEvent(getIdentifier().asString(), messageId, userId));
    }
}
