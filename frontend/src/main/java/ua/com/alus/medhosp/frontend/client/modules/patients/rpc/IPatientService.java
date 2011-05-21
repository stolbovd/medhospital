package ua.com.alus.medhosp.frontend.client.modules.patients.rpc;

import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 02.05.11
 * Time: 11:02
 */
@RemoteServiceRelativePath("springGwtServices/personService")
public interface IPatientService extends RemoteService {
    void createPatient(PatientDTO patientDTO);

    List<PatientDTO> getAllPatients();

    Integer removeSelected(final List<String> ids);

    void savePatient(PatientDTO patientDTO, String[] columns);
}
