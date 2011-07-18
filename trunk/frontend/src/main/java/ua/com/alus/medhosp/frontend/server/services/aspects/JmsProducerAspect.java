package ua.com.alus.medhosp.frontend.server.services.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.codehaus.jackson.map.ObjectMapper;
import ua.com.alus.medhosp.frontend.server.services.spring.TaskService;
import ua.com.alus.medhosp.frontend.shared.TaskDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.TaskColumns;
import ua.com.alus.medhosp.prototype.commands.CommandResult;
import ua.com.alus.medhosp.prototype.data.Constants;
import ua.com.alus.medhosp.prototype.json.CommandJson;
import ua.com.alus.medhosp.prototype.json.CommandsListJson;

import java.io.IOException;

/**
 * Aspect on JMSProducer
 * <p/>
 * Created by Usatov Alexey
 */
@Aspect
public class JmsProducerAspect {

    private Logger logger = Logger.getLogger(JmsProducerAspect.class);
    private TaskService taskService;

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    private ObjectMapper mapper = new ObjectMapper();

    private ObjectMapper getMapper() {
        return mapper;
    }

    @Around(value = "execution(* generateCommands(..)) && args(commandsListJson))", argNames = "pjp,commandsListJson")
    public Object aroundProceedingEvent(ProceedingJoinPoint pjp, CommandsListJson commandsListJson) throws Throwable {
        saveTasks(commandsListJson);
        return pjp.proceed();
    }

    @AfterThrowing(value = "execution(* generateCommands(..)) && args(commandsListJson))", throwing = "e")
    public void afterThrowingException(CommandsListJson commandsListJson, Throwable e) {
        failTasks(commandsListJson, e.getMessage());
    }

    private void saveTasks(CommandsListJson commandsListJson) {
        for (CommandJson commandJson : commandsListJson.getCommands()) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.put(TaskColumns.SUPER_KEY_NAME.getColumnName(), commandJson.getProperties().get(Constants.MESSAGE_ID));
            taskDTO.put(TaskColumns.MESSAGE_ID.getColumnName(), commandJson.getProperties().get(Constants.MESSAGE_ID));
            taskDTO.put(TaskColumns.RESULT.getColumnName(), CommandResult.PENDING.name());
            taskDTO.put(TaskColumns.ENTITY_ID.getColumnName(), getTaskService().getUserId());
            commandJson.getProperties().put(Constants.USER_ID, getTaskService().getUserId());
            try {
                taskDTO.put(TaskColumns.MESSAGE_BODY.getColumnName(), getMapper().writeValueAsString(commandJson));
            } catch (IOException e) {
                logger.error("Error while mapping object to json", e);
            }
            getTaskService().saveTask(taskDTO);
        }
    }

    private void failTasks(CommandsListJson commandsListJson, String errorMessage) {
        for (CommandJson commandJson : commandsListJson.getCommands()) {
            TaskDTO taskDTO = getTaskService().findTask(commandJson.getProperties().get(Constants.USER_ID),
                    commandJson.getProperties().get(Constants.MESSAGE_ID));
            taskDTO.put(TaskColumns.RESULT.getColumnName(), CommandResult.FAIL.name());
            taskDTO.put(TaskColumns.ERROR_MESSAGE.getColumnName(), errorMessage);
            getTaskService().saveTask(taskDTO);
        }
    }

}
