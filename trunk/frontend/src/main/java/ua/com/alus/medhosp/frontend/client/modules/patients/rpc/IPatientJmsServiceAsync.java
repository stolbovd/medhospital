package ua.com.alus.medhosp.frontend.client.modules.patients.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;

/**
 * Async service
 * Create by UsatovAlexey
 */
public interface IPatientJmsServiceAsync {
    public void savePatient(PatientDTO patientDTO, AsyncCallback<Void> callback);
}
