package ua.com.alus.medhosp.backend.axon.api.patient.handlers;

import lombok.Setter;
import org.apache.log4j.Logger;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.repository.AggregateNotFoundException;
import org.axonframework.repository.Repository;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.command.RemovePatientCommand;
import ua.com.alus.medhosp.backend.axon.api.patient.command.SavePatientCommand;
import ua.com.alus.medhosp.backend.domen.entity.patient.Patient;
import ua.com.alus.medhosp.backend.service.PatientService;

import java.util.ArrayList;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 11:30
 */
public class PatientCommandHandler {

    private Logger logger = Logger.getLogger(this.getClass());

    @Setter
    private Repository<PatientAggregate> repository;

    @Setter
    private PatientService patientService;

    @CommandHandler
    public void savePatientCommandHandler(SavePatientCommand command) {
        logger.debug("Handling removePatient command");
        Patient patient = new Patient();
        patient.setEntityId(command.getEntityId());
        patientService.savePatient(patient);

        PatientAggregate patientAggregate;
        try {
            patientAggregate = repository.load(new StringAggregateIdentifier(command.getEntityId()));
        } catch (AggregateNotFoundException agnf) {
            logger.info("Aggregate PatientAggregate not found, adding to repository");
            patientAggregate = new PatientAggregate(command.getEntityId());
            repository.add(patientAggregate);
        }
        patientAggregate.save(command.getMessageId(), command.getUserId());
        logger.debug("Handling savePatient command");
    }

    @CommandHandler
    public void removePatientCommandHandler(RemovePatientCommand command) {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(command.getEntityId());
        patientService.removeSelected(ids);

        PatientAggregate patientAggregate;
        patientAggregate = repository.load(new StringAggregateIdentifier(command.getEntityId()));
        patientAggregate.remove(command.getMessageId(), command.getUserId());
    }
}
