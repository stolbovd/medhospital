package ua.com.alus.medhosp.frontend.server.services.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import ua.com.alus.medhosp.frontend.client.modules.tasks.rpc.ITasksService;
import ua.com.alus.medhosp.frontend.server.services.spring.dao.TaskDao;
import ua.com.alus.medhosp.frontend.shared.TaskDTO;

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
        Authentication authentication;
        if ((authentication = SecurityContextHolder.getContext().getAuthentication()) != null) {
            return ((User)authentication.getPrincipal()).getUsername();
        }
        return ua.com.alus.medhosp.prototype.user.User.SYSTEM.name();
    }

    public String getClock() {
        return String.valueOf(getTaskDao().getKeyspace().createClock());
    }
}
