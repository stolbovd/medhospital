package ua.com.alus.medhosp.frontend.client.modules.patients.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;

import java.util.List;

/**
 * Created by Usatov Alexey
 * Date: 02.05.11
 * Time: 11:02
 */
@RemoteServiceRelativePath("springGwtServices/patientService")
public interface IPatientService extends RemoteService {
    void createPatient(PatientDTO patientDTO);

    List<PatientDTO> getAllPatients();

    void removeSelected(final List<String> ids);

    void savePatientAttributeValue(PatientAttributeValue patientAttributeValue, String[] columns);
}