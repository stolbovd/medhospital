package ua.com.alus.medhosp.backend.axon.api.base;

import org.axonframework.domain.DomainEvent;
import ua.com.alus.medhosp.prototype.cassandra.goals.DtoGoal;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 13:22
 */
public abstract class AbstractEntityEvent extends DomainEvent {
    private String entityId;
    private String messageId;
    private String userId;

    public AbstractEntityEvent(String entityId, String messageId, String userId) {
        this.entityId = entityId;
        this.messageId = messageId;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public abstract DtoGoal getGoal();
}
