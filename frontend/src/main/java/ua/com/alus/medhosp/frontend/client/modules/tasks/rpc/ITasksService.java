package ua.com.alus.medhosp.frontend.client.modules.tasks.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ua.com.alus.medhosp.frontend.shared.TaskDTO;

import java.util.List;

/**
 * Tasks Service
 *
 * Created by Usatov Alexey
 */
@RemoteServiceRelativePath("springGwtServices/taskService")
public interface ITasksService extends RemoteService {

    public void saveTask(TaskDTO taskDTO);
    public List<TaskDTO> getTasks(String key);
}
