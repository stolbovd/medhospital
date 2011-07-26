package ua.com.alus.medhosp.frontend.shared;

import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeColumns;

/**
 * DTO for attribute
 * <p/>
 * Created by Usatov Alexey
 */
public class AttributeDTO extends AbstractDTO {
    public static final String[] COLUMNS;

    static {
        AttributeColumns[] values = AttributeColumns.values();
        COLUMNS = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            COLUMNS[i] = values[i].getColumnName();
        }
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }
}
