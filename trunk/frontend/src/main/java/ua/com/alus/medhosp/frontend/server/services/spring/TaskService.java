package ua.com.alus.medhosp.frontend.server.services.spring;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import ua.com.alus.medhosp.frontend.client.modules.tasks.rpc.ITasksService;
import ua.com.alus.medhosp.frontend.server.services.spring.dao.TaskDao;
import ua.com.alus.medhosp.frontend.shared.TaskDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.TaskColumns;
import ua.com.alus.medhosp.prototype.data.Constants;

import java.util.List;

/**
 * Service for tasks
 */
public class TaskService implements ITasksService {
    private TaskDao taskDao;

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void saveTask(TaskDTO taskDTO) {
        //if userId is not specified - set current user
        if (taskDTO.get(TaskColumns.ENTITY_ID.getColumnName()) == null) {
            taskDTO.put(TaskColumns.ENTITY_ID.getColumnName(), getUserId());
        }
        getTaskDao().save(taskDTO);
    }

    public List<TaskDTO> getTasks() {
        return getTaskDao().find(getUserId(), getUserId());
    }

    public void removeTask(String messageId) {
        getTaskDao().removeSelectedSuperBySuperKeyName(getUserId(), messageId);
    }

    public TaskDTO findTask(String userId, String messageId) {
        if (userId == null) {
            userId = getUserId();
        }
        return getTaskDao().find(userId, userId, messageId).get(0);
    }

    public String getUserId() {
        User user;
        if ((user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()) != null) {
            return user.getUsername();
        }
        return ua.com.alus.medhosp.prototype.user.User.ANONYMOUS.name();
    }

    public String getClock(){
        return String.valueOf(getTaskDao().getKeyspace().createClock());
    }
}
