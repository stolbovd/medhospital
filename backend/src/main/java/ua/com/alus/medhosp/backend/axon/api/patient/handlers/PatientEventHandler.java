package ua.com.alus.medhosp.backend.axon.api.patient.handlers;

import org.apache.log4j.Logger;
import org.axonframework.eventhandling.annotation.EventHandler;
import ua.com.alus.medhosp.backend.axon.api.patient.event.RemovePatientEvent;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SavePatientEvent;
import ua.com.alus.medhosp.backend.domen.entity.patient.Patient;
import ua.com.alus.medhosp.backend.service.PatientService;

import java.util.ArrayList;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 12:09
 */
public class PatientEventHandler {

    private Logger logger = Logger.getLogger(PatientEventHandler.class);

    @EventHandler
    public void savePatientEventHandler(final SavePatientEvent savePatientEvent) {

    }

    @EventHandler
    public void removePatientEventHandler(final RemovePatientEvent removePatientEvent) {

    }
}
