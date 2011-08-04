package ua.com.alus.medhosp.frontend.server.jms;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientService;
import ua.com.alus.medhosp.frontend.server.quartz.MessageUtilsBean;
import ua.com.alus.medhosp.frontend.server.services.spring.TaskService;
import ua.com.alus.medhosp.frontend.shared.*;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeValueColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.PatientColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.TaskColumns;
import ua.com.alus.medhosp.prototype.cassandra.goals.DtoGoal;
import ua.com.alus.medhosp.prototype.commands.CommandResult;
import ua.com.alus.medhosp.prototype.data.Constants;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created Usatov Alexey
 * Date: 27.05.11
 * Time: 11:10
 */
public class JmsCommandListener implements MessageListener {
    private Logger logger = Logger.getLogger(JmsCommandListener.class);

    private IPatientService patientService;

    public IPatientService getPatientService() {
        return patientService;
    }

    public void setPatientService(IPatientService patientService) {
        this.patientService = patientService;
    }

    private Map<String, String> command2Class = new HashMap<String, String>();

    public void setCommand2Class(Map<String, String> command2class) {
        this.command2Class = command2class;
    }

    public Map<String, String> getCommand2Class() {
        return command2Class;
    }

    private TaskService taskService;

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private MessageUtilsBean messageUtilsBean;

    public MessageUtilsBean getMessageUtilsBean() {
        return messageUtilsBean;
    }

    public void setMessageUtilsBean(MessageUtilsBean messageUtilsBean) {
        this.messageUtilsBean = messageUtilsBean;
    }

    /*
   The structure of message:
   {
   key1:value1,
   key2:value2,
   key3:value3
   ...
   }
    */

    @SuppressWarnings("unchecked")
    public void onMessage(Message message) {
        Map<String, String> properties = null;
        try {
            logger.info("Recieved message:" + message.getStringProperty(Constants.COMMAND));
            properties = getObjectMapper().readValue(message.getStringProperty(Constants.COMMAND), HashMap.class);
            if (properties.get(Constants.ERROR) == null) {
                AbstractDTO object = createObject(properties.get(Constants.CLASS), properties);
                executeUpdate(object, object.get(Constants.GOAL));
            }
            updateTask(properties.get(Constants.USER_ID),
                    properties.get(TaskColumns.MESSAGE_ID.getColumnName()),
                    properties.get(Constants.ERROR));
        } catch (Exception e) {
            logger.error(e);
            if (properties != null) {
                logger.info("Requesting for re-send unproceceeded message...");
                getMessageUtilsBean().scheduleReSendCommand(properties.get(TaskColumns.MESSAGE_ID.getColumnName()));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private AbstractDTO createObject(String clazz, Map<String, String> properties) {
        try {
            if (getCommand2Class().get(clazz) == null) {
                logger.trace(String.format("No class is associated for command: %s", clazz));
                return null;
            }
            AbstractDTO resultObject = (AbstractDTO) Class.forName(getCommand2Class().get(clazz)).newInstance();
            setProperties(resultObject, properties);
            return resultObject;
        } catch (Exception e) {
            logger.trace(e);

        }
        return null;
    }

    private void setProperties(Map<String, String> object, Map<String, String> properties) {
        object.putAll(properties);
    }


    //TODO check if it can be simplified with good usability
    private void executeUpdate(AbstractDTO object, String goal) {
        DtoGoal dtoGoal = DtoGoal.valueOf(goal);
        switch (dtoGoal) {
            case SAVE:
                if (object instanceof PatientDTO) {
                    getPatientService().createPatient((PatientDTO) object);
                } else if (object instanceof PatientAttributeValue) {
                    getPatientService().savePatientAttributeValue((PatientAttributeValue) object, object.getColumns());
                } else if (object instanceof AttributeDTO) {
                    getPatientService().savePatientAttribute((AttributeDTO) object);
                }
                break;
            case REMOVE:
                if (object instanceof PatientDTO) {
                    ArrayList<String> ids = new ArrayList<String>();
                    ids.add(object.get(PatientColumns.ENTITY_ID.getColumnName()));
                    getPatientService().removeSelected(ids);
                }else if(object instanceof PatientAttributeValue){
                    getPatientService().removeAttributeValues(
                            object.get(AttributeValueColumns.ENTITY_ID.getColumnName()),
                            object.get(AttributeValueColumns.ATTRIBUTE_ID.getColumnName())
                    );
                }
                break;
        }
    }

    public void updateTask(String userId, String messageId, String errorMessage) {
        TaskDTO taskDTO = getTaskService().findTask(userId, messageId);
        if (errorMessage == null) {
            taskDTO.put(TaskColumns.RESULT.getColumnName(), CommandResult.OK.name());
            getTaskService().saveTask(taskDTO);
        } else {
            taskDTO.put(TaskColumns.RESULT.getColumnName(), CommandResult.FAIL.name());
            taskDTO.put(TaskColumns.ERROR_MESSAGE.getColumnName(), errorMessage);
            getTaskService().saveTask(taskDTO);
        }
    }

}
