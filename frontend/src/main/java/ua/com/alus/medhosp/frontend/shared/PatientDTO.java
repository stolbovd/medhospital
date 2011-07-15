package ua.com.alus.medhosp.frontend.shared;

import ua.com.alus.medhosp.prototype.cassandra.dto.PatientColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usatov Alexey.
 * Date: 22.05.11
 * Time: 16:38
 */
public class PatientDTO extends AbstractDTO {

    private List<PatientAttributeValue> patientAttributeValues;

    public static final String[] COLUMNS;

    static {
        PatientColumns[] values = PatientColumns.values();
        COLUMNS = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            COLUMNS[i] = values[i].getColumnName();
        }
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public List<PatientAttributeValue> getPatientAttributeValues() {
        if (patientAttributeValues == null) {
            patientAttributeValues = new ArrayList<PatientAttributeValue>();
        }
        return patientAttributeValues;
    }

    public void setPatientAttributeValues(List<PatientAttributeValue> patientAttributeValues) {
        this.patientAttributeValues = patientAttributeValues;
    }
}
