package ua.com.alus.medhosp.frontend.client.modules.tasks.ui;

import com.smartgwt.client.widgets.Window;

/**
 * Dialog for task table
 * <p/>
 * Created by Usatov Alexey
 */
public class TasksDialog extends Window {

    private TasksTable tasksTable;

    public TasksDialog() {
        super();
        setTitle("Tasks");
        setWidth(600);
        setHeight(300);
        setIsModal(true);
        setShowMinimizeButton(false);
        setShowModalMask(true);
        centerInPage();
        setShowCloseButton(true);
        setMembers(getTasksTable());

    }

    private TasksTable getTasksTable() {
        if (tasksTable == null) {
            tasksTable = new TasksTable();
        }
        return tasksTable;
    }
}
