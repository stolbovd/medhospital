package ua.com.alus.medhosp.frontend.client.modules.tasks.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ua.com.alus.medhosp.frontend.shared.TaskDTO;

import java.util.List;

public interface ITasksServiceAsync {
    void saveTask(TaskDTO taskDTO, AsyncCallback<Void> async);

    void getTasks(AsyncCallback<List<TaskDTO>> async);
}
