package ua.com.alus.medhosp.backend.axon.api.patient.handlers;

import org.apache.log4j.Logger;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.repository.AggregateNotFoundException;
import org.axonframework.repository.Repository;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAttributeValueAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.command.RemoveAttributeValueCommand;
import ua.com.alus.medhosp.backend.axon.api.patient.command.SaveAttributeValueCommand;

/**
 * Created Usatov Alexey
 * Date: 23.05.11
 * Time: 11:20
 */
public class AttributeValueCommandHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    private Repository<PatientAttributeValueAggregate> repository;

    public void setRepository(Repository<PatientAttributeValueAggregate> repository) {
        this.repository = repository;
    }

    @CommandHandler
    private void saveAttributeValueHandler(SaveAttributeValueCommand saveAttributeValueCommand) {
        PatientAttributeValueAggregate patientAttributeValueAggregate;
        try {
            patientAttributeValueAggregate = repository.load(
                    PatientAttributeValueAggregate.generateIdentifier(saveAttributeValueCommand.getEntityId(),
                            saveAttributeValueCommand.getAttributeId()));
        } catch (AggregateNotFoundException agnf) {
            logger.info("Aggregate PatientAttributeValueAggregate no found in repository - adding new one");
            patientAttributeValueAggregate =
                    new PatientAttributeValueAggregate(saveAttributeValueCommand.getEntityId(), saveAttributeValueCommand.getAttributeId(),
                            saveAttributeValueCommand.getAttributeValue());
            repository.add(patientAttributeValueAggregate);
        }
        patientAttributeValueAggregate.setAttributeValue(saveAttributeValueCommand.getAttributeValue());
        patientAttributeValueAggregate.save(saveAttributeValueCommand.getMessageId());
        logger.info("Handling saveAttributeValue event");
    }

    @CommandHandler
    private void removeAttributeValueHandler(RemoveAttributeValueCommand removeAttributeValueCommand) {
        PatientAttributeValueAggregate aggregate =
                repository.load(PatientAttributeValueAggregate.generateIdentifier(removeAttributeValueCommand.getEntityId(),
                        removeAttributeValueCommand.getAttributeId()));
        aggregate.remove(removeAttributeValueCommand.getMessageId());
        logger.trace("Handling removeAttributeValue event");
    }
}
