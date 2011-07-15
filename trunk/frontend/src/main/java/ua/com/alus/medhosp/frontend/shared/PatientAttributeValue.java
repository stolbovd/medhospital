package ua.com.alus.medhosp.frontend.shared;


import ua.com.alus.medhosp.prototype.data.Constants;

/**
 * Created by Usatov Alexey
 * Date: 02.05.11
 * Time: 1:21
 */

public class PatientAttributeValue extends AbstractDTO implements SuperColumn {


    public static final String[] COLUMNS = {Constants.ENTITY_ID, Constants.ATTRIBUTE_ID,
            Constants.ATTRIBUTE_TYPE, Constants.ATTRIBUTE_VALUE, Constants.ATTRIBUTE_LABEL, Constants.SUPER_KEY_NAME};

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public void setSuperKeyName(String superKeyName) {
        put(Constants.SUPER_KEY_NAME, superKeyName);
    }

    public String getSuperKeyName() {
        return String.valueOf(get(Constants.SUPER_KEY_NAME));
    }
}
