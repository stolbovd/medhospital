package ua.com.alus.medhosp.prototype.cassandra.dto;

/**
 * Columns for Task
 * <p/>
 * Created by Usatov Alexey
 */
public enum TaskColumns {
    ENTITY_ID("entityId"), MESSAGE_ID("messageId"), MESSAGE_BODY("messageBody"),
    SUPER_KEY_NAME("superKeyName"), RESULT("result");

    TaskColumns(String columnName) {
        this.columnName = columnName;
    }

    private String columnName;

    public String getColumnName() {
        return columnName;
    }
}
