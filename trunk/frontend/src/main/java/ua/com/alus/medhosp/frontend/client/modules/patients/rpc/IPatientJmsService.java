package ua.com.alus.medhosp.frontend.client.modules.patients.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;

/**
 * Service
 * <p/>
 * Created by Usatov Alexey
 */
@RemoteServiceRelativePath("springGwtServices/patientJmsProducerService")
public interface IPatientJmsService extends RemoteService {
    public void savePatient(PatientDTO patientDTO);
}
