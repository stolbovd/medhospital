package ua.com.alus.medhosp.prototype.cassandra.dto;

/**
 * Columns of Patient entity
 */
public enum PatientColumns {
    ENTITY_ID("entityId");

    PatientColumns(String columnName) {
        this.columnName = columnName;
    }

    private String columnName;

    public String getColumnName() {
        return columnName;
    }
}
