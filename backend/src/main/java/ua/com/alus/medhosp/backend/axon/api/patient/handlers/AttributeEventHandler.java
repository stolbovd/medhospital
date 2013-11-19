package ua.com.alus.medhosp.backend.axon.api.patient.handlers;

import org.apache.log4j.Logger;
import org.axonframework.eventhandling.annotation.EventHandler;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SaveAttributeEvent;
import ua.com.alus.medhosp.backend.domen.entity.patient.PatientAttribute;
import ua.com.alus.medhosp.backend.service.PatientService;

/**
 * Created Usatov Alexey
 * Date: 24.05.11
 * Time: 14:34
 */
public class AttributeEventHandler {

    private Logger logger = Logger.getLogger(PatientEventHandler.class);

    @EventHandler
    public void saveAttributeEventHandler(SaveAttributeEvent event) {

    }
}
