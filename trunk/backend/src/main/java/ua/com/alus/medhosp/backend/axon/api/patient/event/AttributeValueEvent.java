package ua.com.alus.medhosp.backend.axon.api.patient.event;

import ua.com.alus.medhosp.backend.axon.api.base.AbstractEntityEvent;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 16:52
 */
public abstract class AttributeValueEvent extends AbstractEntityEvent {
    private String patientId;

    public AttributeValueEvent(String entityId, String messageId, String userId) {
        super(entityId, messageId, userId);
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
