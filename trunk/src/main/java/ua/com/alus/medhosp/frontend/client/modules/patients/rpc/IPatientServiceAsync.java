package ua.com.alus.medhosp.frontend.client.modules.patients.rpc;

import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 02.05.11
 * Time: 11:04
 */
public interface IPatientServiceAsync {
    void createPatient(PatientDTO patientDTO, AsyncCallback<Void> callback);

    void getAllPatients(AsyncCallback<List<PatientDTO>> callback);

    void removeSelected(final List<String> ids, AsyncCallback<Integer> async);

    void savePatient(PatientDTO patientDTO, String[] columns, AsyncCallback<Void> async);
}
