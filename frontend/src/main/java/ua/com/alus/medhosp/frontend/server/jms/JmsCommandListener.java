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

    public static final String COMMAND = "command";
    public static final String CLASS = "class";

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
            logger.info("Recieved message:" + message.getStringProperty(COMMAND));
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> properties = mapper.readValue(message.getStringProperty(COMMAND), HashMap.class);
            if (properties.get(Constants.ERROR) == null) {
                AbstractDTO object = createObject(properties.get(CLASS), properties);
                executeUpdate(object);
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

    public void executeUpdate(AbstractDTO object) {
        if (object instanceof PatientDTO) {
            getPatientService().createPatient((PatientDTO) object);
        } else if (object instanceof PatientAttributeValue) {
            getPatientService().savePatientAttributeValue((PatientAttributeValue) object, object.getColumns());
        }
    }

    public void updateTask(String messageId, String result) {
        if (result == null) {
            getTaskService().removeTask(messageId);
        } else {
            TaskDTO taskDTO = getTaskService().findTask(messageId);
            taskDTO.put(TaskColumns.RESULT.getColumnName(), result);
            getTaskService().saveTask(taskDTO);
        }
    }

}
