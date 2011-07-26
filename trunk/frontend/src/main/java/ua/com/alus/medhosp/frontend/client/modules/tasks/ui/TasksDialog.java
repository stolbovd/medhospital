package ua.com.alus.medhosp.frontend.client.modules.tasks.ui;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import ua.com.alus.medhosp.frontend.client.main.ui.ToolBarPanel;
import ua.com.alus.medhosp.frontend.client.main.ui.ToolbarButton;
import ua.com.alus.medhosp.frontend.client.resources.images.Icons;
import ua.com.alus.medhosp.frontend.client.resources.locales.tasks.TasksBundle;
import ua.com.alus.medhosp.frontend.client.utils.Bundle;
import ua.com.alus.medhosp.frontend.client.utils.Constants;

/**
 * Dialog for task table
 * <p/>
 * Created by Usatov Alexey
 */
public class TasksDialog extends Window {

    private TasksTable tasksTable;
    private ToolBarPanel tasksToolbar;
    TasksBundle bundle = Bundle.getInstance().getTasksBundle();
    Icons icons = Constants.getInstance().getIcons();

    public TasksDialog() {
        super();
        setTitle(bundle.tasks());
        setWidth(600);
        setHeight(300);
        setIsModal(true);
        setShowMinimizeButton(false);
        setShowModalMask(true);
        centerInPage();
        setShowCloseButton(true);
        VLayout mainPanel = new VLayout();
        mainPanel.setHeight100();
        mainPanel.setMembers(getTasksToolbar(), getTasksTable());
        addItem(mainPanel);

    }

    private ToolBarPanel getTasksToolbar() {
        if (tasksToolbar == null) {
            tasksToolbar = new ToolBarPanel();

            final ToolbarButton refreshButton = new ToolbarButton(icons.refresh(),
                    bundle.refresh());


            refreshButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    getTasksTable().getController().refreshTable();
                }
            });

            tasksToolbar.setMembers(refreshButton);
            tasksToolbar.setHeight(30);
        }
        return tasksToolbar;
    }

    private TasksTable getTasksTable() {
        if (tasksTable == null) {
            tasksTable = new TasksTable();
        }
        return tasksTable;
    }
}
