package ua.com.alus.medhosp.backend.axon.api.message.event;

import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityEvent;
import ua.com.alus.medhosp.prototype.cassandra.goals.DtoGoal;

/**
 * ReSendMessageEvent
 * <p/>
 * Created by Usatov Alexey
 */
public class ReSendMessageEvent extends AbstractEntityEvent {
    public ReSendMessageEvent(String entityId, String messageId, String userId) {
        super(entityId, messageId, userId);
    }

    @Override
    public DtoGoal getGoal() {
        return DtoGoal.GET;
    }
}
