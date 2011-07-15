package ua.com.alus.medhosp.prototype.cassandra.dto;

/**
 * Base Columns
 * <p/>
 * Created by Usatov Alexey
 */
public enum BaseColumns {
    ENTITY_ID("entityId");

    BaseColumns(String columnName) {
        this.columnName = columnName;
    }

    private String columnName;

    public String getColumnName() {
        return columnName;
    }
}
