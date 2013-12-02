package ua.com.alus.medhosp.backend.axon.api.patient.event;

import lombok.Getter;
import lombok.Setter;
import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityEvent;

import java.io.Serializable;

/**
 * Created Usatov Alexey
 * Date: 24.05.11
 * Time: 14:44
 */
public abstract class AttributeEvent extends AbstractEntityEvent implements Serializable {

    @Setter
    @Getter
    private String type;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String definition;


    public AttributeEvent(String entityId, String messageId, String userId, String name) {
        super(entityId, messageId, userId);
        this.name = name;
    }
}
