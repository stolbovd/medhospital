package ua.com.alus.medhosp.backend.axon.api.base;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.domain.DomainEvent;
import ua.com.alus.medhosp.prototype.cassandra.goals.DtoGoal;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 13:22
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class AbstractEntityEvent extends DomainEvent {
    @Setter
    @Getter
    private String entityId;
    @Setter
    @Getter
    private String messageId;
    @Setter
    @Getter
    private String userId;
    public abstract DtoGoal getGoal();
}
