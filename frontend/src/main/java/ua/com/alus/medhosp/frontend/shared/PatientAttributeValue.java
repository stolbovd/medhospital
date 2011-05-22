package ua.com.alus.medhosp.frontend.shared;


/**
 * Created by Usatov Alexey
 * Date: 02.05.11
 * Time: 1:21
 */

public class PatientAttributeValue extends AbstractDTO implements SuperColumn {

    public static final String ENTITY_ID = "entityId";
    public static final String ATTRIBUTE_ID = "attributeId";
    public static final String ATTRIBUTE_TYPE = "attributeType";
    public static final String ATTRIBUTE_VALUE = "attributeValue";
    public static final String ATTRIBUTE_LABEL = "attributeLabel";


    public static final String[] COLUMNS = {KEY, ENTITY_ID, ATTRIBUTE_ID,
            ATTRIBUTE_TYPE, ATTRIBUTE_VALUE, ATTRIBUTE_LABEL, SUPER_KEY_NAME};

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public void setSuperKeyName(String superKeyName) {
        put(SUPER_KEY_NAME, superKeyName);
    }

    public String getSuperKeyName() {
        return String.valueOf(get(SUPER_KEY_NAME));
    }
}
