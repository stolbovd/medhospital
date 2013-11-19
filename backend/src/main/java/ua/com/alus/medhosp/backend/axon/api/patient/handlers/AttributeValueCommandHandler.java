package ua.com.alus.medhosp.backend.axon.api.patient.handlers;

import org.apache.log4j.Logger;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.AggregateNotFoundException;
import org.axonframework.repository.Repository;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAttributeValueAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.command.RemoveAttributeValueCommand;
import ua.com.alus.medhosp.backend.axon.api.patient.command.SaveAttributeValueCommand;
import ua.com.alus.medhosp.backend.service.PatientService;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 11:20
 */
public class AttributeValueCommandHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    private Repository<PatientAttributeValueAggregate> repository;

    private PatientService patientService;

    public void setRepository(Repository<PatientAttributeValueAggregate> repository) {
        this.repository = repository;
    }

    @CommandHandler
    private void saveAttributeValueCommandHandler(SaveAttributeValueCommand command) {
        patientService.savePatientAttributeValue(command.getEntityId(),
                command.getAttributeId(), command.getAttributeValue());

        PatientAttributeValueAggregate patientAttributeValueAggregate;
        try {
            patientAttributeValueAggregate = repository.load(
                    PatientAttributeValueAggregate.generateIdentifier(command.getEntityId(),
                            command.getAttributeId()));
        } catch (AggregateNotFoundException agnf) {
            logger.info("Aggregate PatientAttributeValueAggregate no found in repository - adding new one");
            patientAttributeValueAggregate =
                    new PatientAttributeValueAggregate(command.getEntityId(), command.getAttributeId(),
                            command.getUserId(),
                            command.getAttributeValue());
            repository.add(patientAttributeValueAggregate);
        }
        patientAttributeValueAggregate.setUserId(command.getUserId());
        patientAttributeValueAggregate.setAttributeValue(command.getAttributeValue());
        patientAttributeValueAggregate.save(command.getMessageId());
        logger.info("Handling saveAttributeValue event");
    }

    @CommandHandler
    private void removeAttributeValueCommandHandler(RemoveAttributeValueCommand command) {
        patientService.removePatientAttributeValue(command.getEntityId(), command.getAttributeId());

        PatientAttributeValueAggregate aggregate =
                repository.load(PatientAttributeValueAggregate.generateIdentifier(command.getEntityId(),
                        command.getAttributeId()));
        aggregate.remove(command.getMessageId());
        logger.trace("Handling removeAttributeValue event");
    }

    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }
}
