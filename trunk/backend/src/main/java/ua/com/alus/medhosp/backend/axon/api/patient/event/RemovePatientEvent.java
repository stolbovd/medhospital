package ua.com.alus.medhosp.backend.axon.api.patient.event;

import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityEvent;
import ua.com.alus.medhosp.prototype.cassandra.goals.DtoGoals;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 12:45
 */
public class RemovePatientEvent extends AbstractEntityEvent {

    public static final String GOAL = DtoGoals.REMOVE.name();

    public RemovePatientEvent(String entityId, String messageId) {
        super(entityId, messageId);
    }
}
