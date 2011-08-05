package ua.com.alus.medhosp.prototype.cassandra.dto;

/**
 * Attribute DTO columns
 * <p/>
 * Create by Usatov Alexey
 */
public enum AttributeColumns {
    ENTITY_ID("entityId"), NAME("name"), DEFINTITION("definition");

    AttributeColumns(String columnName) {
        this.columnName = columnName;
    }

    private String columnName;

    public String getColumnName() {
        return columnName;
    }
}
