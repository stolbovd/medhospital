package ua.com.alus.medhosp.backend.axon.api.patient.event;

import ua.com.alus.medhosp.prototype.cassandra.goals.DtoGoals;

import java.io.Serializable;

/**
 * Created Usatov Alexey
 * Date: 24.05.11
 * Time: 14:27
 */
public class SaveAttributeEvent extends AttributeEvent implements Serializable {

    public static final String GOAL = DtoGoals.SAVE.name();

    public SaveAttributeEvent(String entityId, String messageId, String name) {
        super(entityId, messageId, name);
    }
}
