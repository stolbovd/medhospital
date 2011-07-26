package ua.com.alus.medhosp.frontend.client.modules.tasks.ui;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import ua.com.alus.medhosp.frontend.client.ServiceStorage;
import ua.com.alus.medhosp.frontend.client.modules.tasks.cto.TaskCTO;
import ua.com.alus.medhosp.frontend.shared.TaskDTO;

import java.util.List;

/**
 * TasksController
 * <p/>
 * Created by Usatov Alexey
 */
public class TasksController {

    private TasksTable tasksTable;

    public TasksController(TasksTable tasksTable) {
        this.tasksTable = tasksTable;
    }

    public void refreshTable() {
        ServiceStorage.getInstance().getTasksServiceAsync().getTasks(new AsyncCallback<List<TaskDTO>>() {
            public void onFailure(Throwable caught) {
                SC.say("Error:" + caught);
            }

            public void onSuccess(List<TaskDTO> result) {
                TaskCTO[] patientCTOs = new TaskCTO[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    patientCTOs[i] = getTasksTable().getCTOSample().convertPersonDTO(result.get(i), new TaskCTO());
                }
                getTasksTable().refreshData(patientCTOs);
            }
        });
    }

    public TasksTable getTasksTable() {
        return tasksTable;
    }
}
