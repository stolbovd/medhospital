package ua.com.alus.medhosp.backend.axon.api.patient.event;

import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityEvent;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 11:47
 */
public class SavePatientEvent extends AbstractEntityEvent {

    public SavePatientEvent(String entityId, String messageId) {
        super(entityId, messageId);
    }
}