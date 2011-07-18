package ua.com.alus.medhosp.frontend.server.services.spring;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import ua.com.alus.medhosp.frontend.client.modules.tasks.rpc.ITasksService;
import ua.com.alus.medhosp.frontend.server.services.spring.dao.TaskDao;
import ua.com.alus.medhosp.frontend.shared.TaskDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.TaskColumns;

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
        taskDTO.put(TaskColumns.ENTITY_ID.getColumnName(), getUserId());
        getTaskDao().save(taskDTO);
    }

    public List<TaskDTO> getTasks() {
        return getTaskDao().find(getUserId(), getUserId());
    }

    public void removeTask(String messageId) {
        getTaskDao().removeSelectedSuperBySuperKeyName(getUserId(), messageId);
    }

    public TaskDTO findTask(String messageId) {
        return getTaskDao().find(getUserId(), getUserId(), messageId).get(0);
    }

    private String getUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }
}
