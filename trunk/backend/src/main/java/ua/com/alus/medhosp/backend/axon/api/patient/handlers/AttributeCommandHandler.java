package ua.com.alus.medhosp.backend.axon.api.patient.handlers;

import org.apache.log4j.Logger;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.repository.AggregateNotFoundException;
import org.axonframework.repository.Repository;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAttributeAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.command.SaveAttributeCommand;

/**
 * Created Usatov Alexey
 * Date: 24.05.11
 * Time: 14:27
 */
public class AttributeCommandHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    private Repository<PatientAttributeAggregate> repository;


    @CommandHandler
    public void saveAttributeHandle(SaveAttributeCommand saveAttributeCommand) {
        PatientAttributeAggregate patientAttributeAggregate;
        try {
            patientAttributeAggregate = repository.load(
                    new StringAggregateIdentifier(saveAttributeCommand.getEntityId()));
        } catch (AggregateNotFoundException agnfe) {
            patientAttributeAggregate = new PatientAttributeAggregate(saveAttributeCommand.getEntityId(),
                    saveAttributeCommand.getName());
            repository.add(patientAttributeAggregate);
        }
        patientAttributeAggregate.setType(saveAttributeCommand.getType());
        patientAttributeAggregate.setValueType(saveAttributeCommand.getValueType());
        patientAttributeAggregate.setName(saveAttributeCommand.getName());

        patientAttributeAggregate.save(saveAttributeCommand.getMessageId());
        logger.info("Handling SaveAttributeCommand");
    }

    public void setRepository(Repository<PatientAttributeAggregate> repository) {
        this.repository = repository;
    }
}
