package ua.com.alus.medhosp.frontend.shared;


import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeValueColumns;

/**
 * Created by Usatov Alexey
 * Date: 02.05.11
 * Time: 1:21
 */

public class PatientAttributeValue extends AbstractDTO implements SuperColumn {


    public static final String[] COLUMNS;

    static{
        AttributeValueColumns [] values = AttributeValueColumns.values();
        COLUMNS = new String[values.length];
        for(int i=0; i< values.length; i++){
            COLUMNS[i] = values[i].getColumnName();
        }
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public void setSuperKeyName(String superKeyName) {
        put(AttributeValueColumns.SUPER_KEY_NAME.getColumnName(), superKeyName);
    }

    public String getSuperKeyName() {
        return String.valueOf(get(AttributeValueColumns.SUPER_KEY_NAME.getColumnName()));
    }
}
