package ua.com.alus.medhosp.backend.axon.api.patient.handlers;

import org.apache.log4j.Logger;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.repository.AggregateNotFoundException;
import org.axonframework.repository.Repository;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.command.RemovePatientCommand;
import ua.com.alus.medhosp.backend.axon.api.patient.command.SavePatientCommand;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 11:30
 */
public class PatientCommandHandler {

    private Logger logger = Logger.getLogger(this.getClass());

    private Repository<PatientAggregate> repository;

    @CommandHandler
    public void savePatient(SavePatientCommand savePatientCommand) {
        PatientAggregate patientAggregate;
        try {
            patientAggregate = repository.load(new StringAggregateIdentifier(savePatientCommand.getEntityId()));
        } catch (AggregateNotFoundException agnf) {
            logger.info("Aggregate PatientAggregate not found, adding to repository");
            patientAggregate = new PatientAggregate(savePatientCommand.getEntityId());
            repository.add(patientAggregate);
        }
        patientAggregate.save(savePatientCommand.getMessageId(), savePatientCommand.getUserId());
        logger.debug("Handling savePatient command");
    }

    @CommandHandler
    public void removePatient(RemovePatientCommand removePatientCommand) {
        logger.debug("Handling removePatient command");
        PatientAggregate patientAggregate;
        patientAggregate = repository.load(new StringAggregateIdentifier(removePatientCommand.getEntityId()));
        patientAggregate.remove(removePatientCommand.getMessageId(), removePatientCommand.getUserId());
    }

    public void setRepository(Repository<PatientAggregate> repository) {
        this.repository = repository;
    }
}
