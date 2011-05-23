package ua.com.alus.medhosp.backend.axon.api.patient.handlers;

import org.apache.log4j.Logger;
import org.axonframework.eventhandling.annotation.EventHandler;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SaveAttributeValueEvent;
import ua.com.alus.medhosp.backend.service.PatientService;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 12:02
 */
public class AttributeValueEventHandler {
    private Logger logger = Logger.getLogger(PatientEventHandler.class);

    private PatientService patientService;

    @EventHandler
    public void saveAttributeValueHandler(SaveAttributeValueEvent event){
        patientService.savePatientAttributeValue(event.getEntityId(), event.getAttributeId(), event.getAttributeValue());
    }

    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }
}
