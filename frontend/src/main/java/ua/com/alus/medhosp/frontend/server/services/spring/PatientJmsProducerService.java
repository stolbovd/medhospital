package ua.com.alus.medhosp.frontend.server.services.spring;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientJmsService;
import ua.com.alus.medhosp.frontend.server.jms.JmsCommandProducer;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import ua.com.alus.medhosp.frontend.shared.TaskDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.TaskColumns;
import ua.com.alus.medhosp.prototype.commands.Command;
import ua.com.alus.medhosp.prototype.commands.CommandResult;
import ua.com.alus.medhosp.prototype.data.Constants;
import ua.com.alus.medhosp.prototype.json.CommandJson;
import ua.com.alus.medhosp.prototype.json.CommandsListJson;

import java.io.IOException;

/**
 * Class that generate JMS-message for creating patient, attributes and attributeValues.
 * <p/>
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

    private ObjectMapper mapper = new ObjectMapper();

    private ObjectMapper getMapper() {
        return mapper;
    }

    public void savePatient(PatientDTO patientDTO) {
        CommandsListJson commandsListJson = new CommandsListJson();
        CommandJson savePatientCommand = new CommandJson();
        savePatientCommand.setCommand(Command.SAVE_PATIENT.getCommandName());
        savePatientCommand.getProperties().put(BaseColumns.ENTITY_ID.getColumnName(), patientDTO.get(BaseColumns.ENTITY_ID.getColumnName()));
        savePatientCommand.getProperties().put(Constants.MESSAGE_ID, patientDTO.get(Constants.MESSAGE_ID));
        commandsListJson.getCommands().add(savePatientCommand);
        sendJms(commandsListJson);
    }

    public void sendJms(CommandsListJson commandsListJson) {
        getJmsCommandProducer().generateCommands(commandsListJson);
    }

}