package ua.com.alus.medhosp.backend.axon.api.patient.handlers;

import org.apache.log4j.Logger;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAttributeValueAggregate;
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

    }
}
