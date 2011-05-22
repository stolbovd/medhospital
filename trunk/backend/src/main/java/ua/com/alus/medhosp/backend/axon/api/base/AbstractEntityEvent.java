package ua.com.alus.medhosp.backend.axon.api.base;

import org.axonframework.domain.DomainEvent;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 13:22
 */
public abstract class AbstractEntityEvent extends DomainEvent {
    private String entityId;
    private String messageId;

    public AbstractEntityEvent(String entityId, String messageId) {
        this.entityId = entityId;
        this.messageId = messageId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
