package ua.com.alus.medhosp.frontend.shared;


/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 02.05.11
 * Time: 1:21
 */

public class PatientDTO extends AbstractDTO implements SuperColumn {
    public static final String MAIN = "main";

    public static final String LASTNAME = "lastName";
    public static final String NAME = "name";


    public static final String[] COLUMNS = {KEY, NAME, LASTNAME};

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public String getSuperKeyName() {
        return MAIN;
    }
}
