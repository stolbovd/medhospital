package ua.com.alus.medhosp.frontend.shared;

import ua.com.alus.medhosp.prototype.data.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usatov Alexey.
 * Date: 22.05.11
 * Time: 16:38
 */
public class PatientDTO extends AbstractDTO {

    private List<PatientAttributeValue> patientAttributeValues;

    public static final String[] COLUMNS = {Constants.KEY};

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
