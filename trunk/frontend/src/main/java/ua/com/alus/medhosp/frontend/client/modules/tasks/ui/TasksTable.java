package ua.com.alus.medhosp.frontend.client.modules.tasks.ui;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import ua.com.alus.medhosp.frontend.client.modules.patients.ui.SimpleField;
import ua.com.alus.medhosp.frontend.client.modules.tasks.cto.TaskCTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.TaskColumns;

/**
 * Tasks list
 * <p/>
 * Created by Usatov
 */
public class TasksTable extends ListGrid {
    private TaskCTO taskCTO = new TaskCTO();

    //TODO check if it can be removed.
    public TaskCTO getCTOSample() {
        return taskCTO;
    }

    public TasksTable() {
        initTable();
        controller = new TasksController(this);
    }

    private void initTable() {

        SimpleField resultColumn = new SimpleField(TaskColumns.RESULT.getColumnName(), "result");
        resultColumn.setType(ListGridFieldType.TEXT);
        resultColumn.setCanEdit(false);

        resultColumn.setAlign(Alignment.CENTER);
        resultColumn.setType(ListGridFieldType.IMAGE);
        resultColumn.setImageSize(20);
        resultColumn.setWidth(30);
        resultColumn.setImageURLPrefix("icons/tasks/");
        resultColumn.setImageURLSuffix(".png");


        SimpleField messageBody = new SimpleField(TaskColumns.MESSAGE_BODY.getColumnName(), "messageBody");
        messageBody.setType(ListGridFieldType.TEXT);
        messageBody.setCanEdit(false);

        messageBody.setHoverCustomizer(new HoverCustomizer() {
            public String hoverHTML(Object o, ListGridRecord listGridRecord, int i, int i1) {
                return listGridRecord.getAttributeAsString(TaskColumns.MESSAGE_BODY.getColumnName());
            }
        });
        messageBody.setShowHover(true);

        setFields(resultColumn, messageBody);

        setAutoFetchData(true);
        setTop(30);
        setWidth100();
        setHeight100();

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
