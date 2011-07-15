package ua.com.alus.medhosp.frontend.server.services.spring;

import org.apache.log4j.Logger;
import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientJmsService;
import ua.com.alus.medhosp.frontend.server.jms.JmsCommandProducer;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import ua.com.alus.medhosp.prototype.commands.Command;
import ua.com.alus.medhosp.prototype.data.Constants;
import ua.com.alus.medhosp.prototype.json.CommandJson;
import ua.com.alus.medhosp.prototype.json.CommandsListJson;

import javax.jms.JMSException;

/**
 * Class that generate JMS-message for creating patient, attributes and attributeValues.
 *
 * Created by Usatov Alexey.
 */
public class PatientJmsProducerService implements IPatientJmsService {
    private Logger logger = Logger.getLogger(PatientJmsProducerService.class);

    private JmsCommandProducer jmsCommandProducer;

    public JmsCommandProducer getJmsCommandProducer() {
        return jmsCommandProducer;
    }

    public void setJmsCommandProducer(JmsCommandProducer jmsCommandProducer) {
        this.jmsCommandProducer = jmsCommandProducer;
    }

    public void savePatient(PatientDTO patientDTO){
        CommandsListJson commandsListJson = new CommandsListJson();
        CommandJson savePatientCommand = new CommandJson();
        savePatientCommand.setCommand(Command.SAVE_PATIENT.getCommandName());
        savePatientCommand.getProperties().put(Constants.ENTITY_ID, patientDTO.get(Constants.ENTITY_ID));
        savePatientCommand.getProperties().put(Constants.MESSAGE_ID, patientDTO.get(Constants.MESSAGE_ID));
        commandsListJson.getCommands().add(savePatientCommand);
        try {
            getJmsCommandProducer().generateCommands(commandsListJson);
        } catch (JMSException e) {
            logger.error("Error while proceeding savePatient:", e);
        }
    }
}