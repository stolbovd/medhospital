package ua.com.alus.medhosp.frontend.client.modules.patients.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import ua.com.alus.medhosp.frontend.client.ServiceStorage;
import ua.com.alus.medhosp.frontend.client.modules.patients.cto.PatientCTO;
import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usatov Alexey
 * Date: 03.05.11
 * Time: 11:19
 */
public class PatientsController implements IController {
    private PatientsTable patientsTable;

    public PatientsTable getPatientsTable() {
        return patientsTable;
    }

    public PatientsController(PatientsTable patientsTable) {
        this.patientsTable = patientsTable;
    }

    public void refreshTable(String entityId) {
        ServiceStorage.getInstance().getPatientServiceAsync().getPatients(entityId, new AsyncCallback<List<PatientDTO>>() {
            public void onFailure(Throwable caught) {
                SC.say("Error:" + caught);
            }

            public void onSuccess(List<PatientDTO> result) {
                PatientCTO[] patientCTOs = new PatientCTO[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    patientCTOs[i] = getPatientsTable().getPatCTOSample().convertPersonDTO(result.get(i), new PatientCTO());
                }
                getPatientsTable().refreshData(patientCTOs);
            }
        });
    }

    public void savePatientAttributeValue(PatientAttributeValue patientDTO, String... column) {

    }

    public void createPatient(final PatientDTO patientDTO) {
        ServiceStorage.getInstance().getPatientJmsServiceAsync().savePatient(patientDTO, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
                SC.say("Error:" + caught);
            }

            public void onSuccess(Void result) {
                refreshTable(patientDTO.get(BaseColumns.ENTITY_ID.getColumnName()));
            }
        });
    }

    public void removeSelected() {
        final List<String> ids = new ArrayList<String>();
        for (String key : getPatientsTable().getSelectedPatients()) {
            ids.add(String.valueOf(key));
        }
        ServiceStorage.getInstance().getPatientJmsServiceAsync().removePatients(ids, new AsyncCallback<Void>() {
            public void onFailure(Throwable throwable) {
                SC.say("Error:" + throwable);
            }

            public void onSuccess(Void aVoid) {
                SC.say("Success");
            }
        });
    }
}
