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
import ua.com.alus.medhosp.prototype.commands.Command;
import ua.com.alus.medhosp.prototype.commands.CommandResult;
import ua.com.alus.medhosp.prototype.data.Constants;
import ua.com.alus.medhosp.prototype.json.CommandJson;
import ua.com.alus.medhosp.prototype.json.CommandsListJson;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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

    private ICommandProducer commandProducer;

    public ICommandProducer getCommandProducer() {
        return commandProducer;
    }

    public void setCommandProducer(ICommandProducer commandProducer) {
        this.commandProducer = commandProducer;
    }

    private int resendDelay = 10;

    public void setResendDelay(int resendDelay) {
        this.resendDelay = resendDelay;
    }

    public int getResendDelay() {
        return resendDelay;
    }

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);


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
            ObjectMapper mapper = new ObjectMapper();
            properties = mapper.readValue(message.getStringProperty(Constants.COMMAND), HashMap.class);
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
                scheduleReSendCommand(properties.get(TaskColumns.MESSAGE_ID.getColumnName()));
            }
        }
    }

    private void scheduleReSendCommand(final String messageId) {
        final Runnable reSendMessage = new Runnable() {
            public void run() {
                try {
                    getCommandProducer().generateCommands(getResendCommandList(messageId));
                } catch (Throwable e) {
                    logger.error(e);
                    logger.info("Re-scheduling request for re-send unproceceeded message...");
                    scheduleReSendCommand(messageId);
                }
            }
        };
        scheduler.schedule(reSendMessage, getResendDelay(), TimeUnit.SECONDS);
    }

    private synchronized CommandsListJson getResendCommandList(String messageId) {
        CommandsListJson commandsListJson = new CommandsListJson();
        CommandJson reSendMessCommand = new CommandJson();
        reSendMessCommand.setCommand(Command.RESEND_MESSAGE.getCommandName());
        reSendMessCommand.getProperties().put(TaskColumns.ENTITY_ID.getColumnName(), messageId);
        commandsListJson.getCommands().add(reSendMessCommand);
        return commandsListJson;
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
