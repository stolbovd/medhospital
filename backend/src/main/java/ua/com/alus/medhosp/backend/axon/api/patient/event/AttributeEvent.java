package ua.com.alus.medhosp.backend.axon.api.patient.event;

import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityEvent;

import java.io.Serializable;

/**
 * Created Usatov Alexey
 * Date: 24.05.11
 * Time: 14:44
 */
public abstract class AttributeEvent extends AbstractEntityEvent implements Serializable {
    private String type;
    private String name;
    private String definition;


    public AttributeEvent(String entityId, String messageId, String userId, String name) {
        super(entityId, messageId, userId);
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
