package ua.com.alus.medhosp.frontend.server.jms;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientService;
import ua.com.alus.medhosp.frontend.server.services.spring.TaskService;
import ua.com.alus.medhosp.frontend.shared.AbstractDTO;
import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import ua.com.alus.medhosp.frontend.shared.TaskDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.TaskColumns;
import ua.com.alus.medhosp.prototype.cassandra.goals.DtoGoal;
import ua.com.alus.medhosp.prototype.commands.CommandResult;
import ua.com.alus.medhosp.prototype.data.Constants;

import javax.jms.Message;
import javax.jms.MessageListener;
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
        try {
            logger.info("Recieved message:" + message.getStringProperty(Constants.COMMAND));
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> properties = mapper.readValue(message.getStringProperty(Constants.COMMAND), HashMap.class);
            if (properties.get(Constants.ERROR) == null) {
                AbstractDTO object = createObject(properties.get(Constants.CLASS), properties);
                executeUpdate(object, object.get(Constants.GOAL));
            }
            updateTask(properties.get(TaskColumns.MESSAGE_ID.getColumnName()), properties.get(Constants.ERROR));
        } catch (Exception e) {
            logger.trace(e);
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
        switch (dtoGoal){
            case SAVE:
                if (object instanceof PatientDTO) {
                    getPatientService().createPatient((PatientDTO) object);
                } else if (object instanceof PatientAttributeValue) {
                    getPatientService().savePatientAttributeValue((PatientAttributeValue) object, object.getColumns());
                }
                break;
        }
    }

    public void updateTask(String messageId, String result) {
        TaskDTO taskDTO = getTaskService().findTask(messageId);
        if (result == null) {
            taskDTO.put(TaskColumns.RESULT.getColumnName(), CommandResult.OK.name());
            getTaskService().removeTask(messageId);
        } else {
            taskDTO.put(TaskColumns.RESULT.getColumnName(), CommandResult.FAIL.name());
            taskDTO.put(TaskColumns.ERROR_MESSAGE.getColumnName(), result);
            getTaskService().saveTask(taskDTO);
        }
    }

}
