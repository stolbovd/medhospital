package ua.com.alus.medhosp.frontend.client.modules.patients.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ua.com.alus.medhosp.frontend.shared.AttributeDTO;
import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;

import java.util.List;

/**
 * Created by Usatov Alexey
 * Date: 02.05.11
 * Time: 11:04
 */
public interface IPatientServiceAsync {
    void createPatient(PatientDTO patientDTO, AsyncCallback<Void> callback);

    void getAllPatients(AsyncCallback<List<PatientDTO>> callback);

    void removeSelected(final List<String> ids, AsyncCallback<Void> async);

    void savePatientAttributeValue(PatientAttributeValue patientAttributeValue, String[] columns, AsyncCallback<Void> async);

    void savePatientAttribute(AttributeDTO attributeDTO, AsyncCallback<Void> async);
}
