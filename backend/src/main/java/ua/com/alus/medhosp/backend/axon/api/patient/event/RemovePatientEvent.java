package ua.com.alus.medhosp.backend.axon.api.patient.event;

import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityEvent;
import ua.com.alus.medhosp.prototype.cassandra.goals.DtoGoal;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 12:45
 */
public class RemovePatientEvent extends AbstractEntityEvent {


    public RemovePatientEvent(String entityId, String messageId, String userId) {
        super(entityId, messageId, userId);
    }

    @Override
    public DtoGoal getGoal() {
        return DtoGoal.REMOVE;
    }
}
