package ua.com.alus.medhosp.frontend.server.services.spring;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientJmsService;
import ua.com.alus.medhosp.frontend.server.jms.JmsCommandProducer;
import ua.com.alus.medhosp.frontend.shared.AttributeDTO;
import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeValueColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.PatientColumns;
import ua.com.alus.medhosp.prototype.commands.Command;
import ua.com.alus.medhosp.prototype.json.CommandJson;
import ua.com.alus.medhosp.prototype.json.CommandsListJson;

import java.util.List;

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
        commandsListJson.getCommands().add(savePatientCommand);
        sendJms(commandsListJson);
    }

    public void saveAttribute(AttributeDTO attributeDTO) {
        CommandsListJson commandsListJson = new CommandsListJson();
        CommandJson saveAttributeCommand = new CommandJson();
        saveAttributeCommand.setCommand(Command.SAVE_ATTRIBUTE.getCommandName());
        saveAttributeCommand.getProperties().put(AttributeColumns.ENTITY_ID.getColumnName(), attributeDTO.get(AttributeColumns.ENTITY_ID.getColumnName()));
        saveAttributeCommand.getProperties().put(AttributeColumns.NAME.getColumnName(), attributeDTO.get(AttributeColumns.NAME.getColumnName()));
        commandsListJson.getCommands().add(saveAttributeCommand);
        sendJms(commandsListJson);
    }

    public void saveAttributeValue(PatientAttributeValue patientAttributeValue) {
        CommandsListJson commandsListJson = new CommandsListJson();
        CommandJson saveAttributeValueCommand = new CommandJson();
        saveAttributeValueCommand.setCommand(Command.SAVE_ATTRIBUTE_VALUE.getCommandName());
        saveAttributeValueCommand.getProperties().put(AttributeValueColumns.SUPER_KEY_NAME.getColumnName(), patientAttributeValue.getSuperKeyName());
        saveAttributeValueCommand.getProperties().put(AttributeValueColumns.ENTITY_ID.getColumnName(), patientAttributeValue.get(AttributeValueColumns.ENTITY_ID.getColumnName()));
        saveAttributeValueCommand.getProperties().put(AttributeValueColumns.ATTRIBUTE_ID.getColumnName(), patientAttributeValue.get(AttributeValueColumns.ATTRIBUTE_ID.getColumnName()));
        saveAttributeValueCommand.getProperties().put(AttributeValueColumns.ATTRIBUTE_VALUE.getColumnName(), patientAttributeValue.get(AttributeValueColumns.ATTRIBUTE_VALUE.getColumnName()));
        commandsListJson.getCommands().add(saveAttributeValueCommand);
        sendJms(commandsListJson);
    }

    public void removePatients(List<String> ids){
        CommandsListJson commandsListJson = new CommandsListJson();
        for(String id : ids){
        CommandJson removePatientCommand = new CommandJson();
            removePatientCommand.setCommand(Command.REMOVE_PATIENT.getCommandName());
            removePatientCommand.getProperties().put(PatientColumns.ENTITY_ID.getColumnName(), id);
            commandsListJson.getCommands().add(removePatientCommand);
        }
        sendJms(commandsListJson);
    }

    public void sendJms(CommandsListJson commandsListJson) {
        getJmsCommandProducer().generateCommands(commandsListJson);
    }

}
