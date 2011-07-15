package ua.com.alus.medhosp.frontend.shared;

import ua.com.alus.medhosp.prototype.cassandra.dto.TaskColumns;

/**
 * DTO of Task(command)
 * <p/>
 * Created by Usatov
 */
public class TaskDTO extends AbstractDTO implements SuperColumn {

    public static final String[] COLUMNS;

    static {
        TaskColumns[] values = TaskColumns.values();
        COLUMNS = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            COLUMNS[i] = values[i].getColumnName();
        }
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public void setSuperKeyName(String superKeyName) {
        put(TaskColumns.SUPER_KEY_NAME.getColumnName(), superKeyName);
    }

    public String getSuperKeyName() {
        return String.valueOf(get(TaskColumns.SUPER_KEY_NAME.getColumnName()));
    }

}
