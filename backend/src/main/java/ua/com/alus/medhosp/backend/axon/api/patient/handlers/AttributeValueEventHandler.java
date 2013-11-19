package ua.com.alus.medhosp.backend.axon.api.patient.handlers;

import org.apache.log4j.Logger;
import org.axonframework.eventhandling.annotation.EventHandler;
import ua.com.alus.medhosp.backend.axon.api.patient.event.RemoveAttributeValueEvent;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SaveAttributeValueEvent;
import ua.com.alus.medhosp.backend.service.PatientService;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 12:02
 */
public class AttributeValueEventHandler {
    private Logger logger = Logger.getLogger(PatientEventHandler.class);

    @EventHandler
    public void saveAttributeValueEventHandler(SaveAttributeValueEvent event) {

    }

    @EventHandler
    public void removeAttributeValueEventHandler(RemoveAttributeValueEvent event) {

    }
}
