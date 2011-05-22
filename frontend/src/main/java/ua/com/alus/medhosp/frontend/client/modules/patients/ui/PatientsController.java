package ua.com.alus.medhosp.frontend.client.modules.patients.ui;

import ua.com.alus.medhosp.frontend.client.ServiceStorage;
import ua.com.alus.medhosp.frontend.client.modules.patients.cto.PatientCTO;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;

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
        this.patientsTable.setController(this);
        refreshTable();
    }

    public void refreshTable() {
        ServiceStorage.getInstance().getPatientServiceAsync().getAllPatients(new AsyncCallback<List<PatientDTO>>() {
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

    public void savePatient(PatientDTO patientDTO, String... column) {
        ServiceStorage.getInstance().getPatientServiceAsync().savePatient(patientDTO, column, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
                SC.say("Error:" + caught);
            }

            public void onSuccess(Void result) {
                refreshTable();
            }
        });
    }

    public void removeSelected() {
        final List<String> ids = new ArrayList<String>();
        for (String key : getPatientsTable().getSelectedPatients()) {
            ids.add(String.valueOf(key));
        }
        if (ids.size() == 0) {
            SC.say("Nothing is selected!");
            return;
        }
        ServiceStorage.getInstance().getPatientServiceAsync().removeSelected(ids, new AsyncCallback<Integer>() {
            public void onFailure(Throwable caught) {
                SC.say("Error:" + caught);
            }

            public void onSuccess(Integer result) {
                refreshTable();
            }
        });
    }
}
