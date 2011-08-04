package ua.com.alus.medhosp.frontend.client.modules.patients.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ua.com.alus.medhosp.frontend.shared.AttributeDTO;
import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;

import java.util.List;

/**
 * Service
 * <p/>
 * Created by Usatov Alexey
 */
@RemoteServiceRelativePath("springGwtServices/patientJmsProducerService")
public interface IPatientJmsService extends RemoteService {
    public void savePatient(PatientDTO patientDTO);

    public void saveAttribute(AttributeDTO attributeDTO);

    void saveAttributeValue(PatientAttributeValue patientAttributeValue);

    void removePatients(List<String> ids);

    void removeSelectedAttrValues(String entityId, List<String> attrIds);
}
