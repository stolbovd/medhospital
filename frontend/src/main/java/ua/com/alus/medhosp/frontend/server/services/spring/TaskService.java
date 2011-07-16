package ua.com.alus.medhosp.frontend.server.services.spring;

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

    public List<TaskDTO> getTasks(String key) {
        return getTaskDao().find(key, key);
    }

    public void removeTask(String messageId) {
        getTaskDao().removeSelectedSuperBySuperKeyName("FAKE_user_id", messageId);
    }

    public TaskDTO findTask(String messageId){
        return getTaskDao().find("FAKE_user_id", "FAKE_user_id", messageId).get(0);
    }
}
