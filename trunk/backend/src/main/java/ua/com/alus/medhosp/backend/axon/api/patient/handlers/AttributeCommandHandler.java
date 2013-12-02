package ua.com.alus.medhosp.backend.axon.api.patient.handlers;

import lombok.Setter;
import org.apache.log4j.Logger;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.repository.AggregateNotFoundException;
import org.axonframework.repository.Repository;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAttributeAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.command.SaveAttributeCommand;
import ua.com.alus.medhosp.backend.domen.entity.patient.PatientAttribute;
import ua.com.alus.medhosp.backend.service.PatientService;

/**
 * Created Usatov Alexey
 * Date: 24.05.11
 * Time: 14:27
 */
public class AttributeCommandHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    @Setter
    private Repository<PatientAttributeAggregate> repository;

    @Setter
    private PatientService patientService;


    @CommandHandler
    public void saveAttributeCommandHandler(SaveAttributeCommand command) {
        PatientAttribute attribute = patientService.getPatientAttributeDao().findById(command.getEntityId());
        if (attribute == null) {
            attribute = new PatientAttribute();
            attribute.setEntityId(command.getEntityId());
        }
        attribute.setName(command.getName());
        attribute.setType(command.getType());
        attribute.setDefinition(command.getDefinition());
        patientService.savePatientAttribute(attribute);
        logger.info("Handled SaveAttributeEvent");

        PatientAttributeAggregate patientAttributeAggregate;
        try {
            patientAttributeAggregate = repository.load(
                    new StringAggregateIdentifier(command.getEntityId()));
        } catch (AggregateNotFoundException agnfe) {
            patientAttributeAggregate = new PatientAttributeAggregate(command.getEntityId(),
                    command.getName());
            repository.add(patientAttributeAggregate);
        }
        patientAttributeAggregate.setType(command.getType());
        patientAttributeAggregate.setDefinition(command.getDefinition());
        patientAttributeAggregate.setName(command.getName());
        patientAttributeAggregate.save(command.getMessageId(), command.getUserId());

        logger.info("Handling SaveAttributeCommand");
    }
}
