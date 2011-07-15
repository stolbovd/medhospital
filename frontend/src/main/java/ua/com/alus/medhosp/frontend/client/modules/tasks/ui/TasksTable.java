package ua.com.alus.medhosp.frontend.client.modules.tasks.ui;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import ua.com.alus.medhosp.frontend.client.modules.patients.ui.SimpleField;
import ua.com.alus.medhosp.frontend.client.modules.tasks.cto.TaskCTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.TaskColumns;

/**
 * Tasks list
 * <p/>
 * Created by Usatov
 */
public class TasksTable extends ListGrid {
    private TaskCTO taskCTO = new TaskCTO();

    //TODO check if it can be removed.
    public TaskCTO getCTOSample(){
        return taskCTO;
    }
    public TasksTable() {
        initTable();
        controller = new TasksController(this);
    }

    private void initTable() {

        SimpleField entityIdColumn = new SimpleField(TaskColumns.ENTITY_ID.getColumnName(), "id");
        entityIdColumn.setType(ListGridFieldType.TEXT);
        entityIdColumn.setCanEdit(false);

        SimpleField messageId = new SimpleField(TaskColumns.MESSAGE_ID.getColumnName(), "messageId");
        messageId.setType(ListGridFieldType.TEXT);
        messageId.setCanEdit(false);

        SimpleField messageBody = new SimpleField(TaskColumns.MESSAGE_BODY.getColumnName(), "messageBody");
        messageBody.setType(ListGridFieldType.TEXT);
        messageBody.setCanEdit(false);

        setFields(entityIdColumn, messageId, messageBody);

        setAutoFetchData(true);
        setTop(30);
        setWidth(200);
        setHeight(500);

    }

    private TasksController controller;

    public TasksController getController() {
        return controller;
    }

    public void setController(TasksController controller) {
        this.controller = controller;
    }

    public void refreshData(TaskCTO[] taskCtos) {
        setData(taskCtos);
        refreshFields();
    }
}
