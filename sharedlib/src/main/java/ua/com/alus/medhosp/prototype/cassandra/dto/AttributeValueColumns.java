package ua.com.alus.medhosp.prototype.cassandra.dto;

/**
 * Columns of AttributeValueColumns
 * <p/>
 * Created by Usatov Alexey
 */
public enum AttributeValueColumns {
    ENTITY_ID("entityId"), ATTRIBUTE_ID("attributeId"), ATTRIBUTE_VALUE("attributeValue"),
    ATTRIBUTE_LABEL("attributeLabel"), ATTRIBUTE_TYPE("attributeType"), SUPER_KEY_NAME("superKeyName");

    AttributeValueColumns(String columnName) {
        this.columnName = columnName;
    }

    private String columnName;

    public String getColumnName() {
        return columnName;
    }
}
