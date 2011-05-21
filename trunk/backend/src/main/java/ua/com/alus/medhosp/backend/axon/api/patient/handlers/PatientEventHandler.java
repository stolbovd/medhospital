package ua.com.alus.medhosp.backend.axon.api.patient.handlers;

import org.apache.log4j.Logger;
import org.axonframework.eventhandling.annotation.EventHandler;
import ua.com.alus.medhosp.backend.axon.api.patient.event.RemovePatientEvent;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SavePatientEvent;
import ua.com.alus.medhosp.backend.domen.entity.patient.Patient;
import ua.com.alus.medhosp.backend.service.PatientService;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Usatov Alexey
 * Date: 19.05.11
 * Time: 12:09
 */
public class PatientEventHandler {

    private Logger logger = Logger.getLogger(PatientEventHandler.class);

    private PatientService patientService;


    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }


    @EventHandler
    public void savePatient(final SavePatientEvent savePatientEvent) {
        Patient patient = new Patient();
        patient.setEntityId(savePatientEvent.getEntityId());
        patientService.savePatient(patient);
    }

    @EventHandler
    public void removePatient(final RemovePatientEvent removePatientEvent) {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(removePatientEvent.getEntityId());
        patientService.removeSelected(ids);
    }
}
