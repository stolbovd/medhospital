package ua.com.alus.medhosp.frontend.server.services.spring;

import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientService;
import ua.com.alus.medhosp.frontend.server.services.spring.dao.PatientAttributeValueDao;
import ua.com.alus.medhosp.frontend.server.services.spring.dao.PatientDao;
import ua.com.alus.medhosp.frontend.shared.AbstractDTO;
import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;

import java.util.List;

/**
 * Created by Usatov Alexey
 * Date: 02.05.11
 * Time: 10:49
 */
public class PatientServiceImpl implements IPatientService {

    private PatientDao patientDao;

    public PatientDao getPatientDao() {
        return patientDao;
    }

    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    private PatientAttributeValueDao patientAttributeValueDao;

    public PatientAttributeValueDao getPatientAttributeValueDao() {
        return patientAttributeValueDao;
    }

    public void setPatientAttributeValueDao(PatientAttributeValueDao patientAttributeValueDao) {
        this.patientAttributeValueDao = patientAttributeValueDao;
    }

    public void createPatient(PatientDTO gwtPatient) {
        getPatientDao().save(gwtPatient);
    }

    public List<PatientDTO> getAllPatients() {
        List<PatientDTO> patients = getPatientDao().find("", "");
        for (PatientDTO patientDTO : patients) {
            String key = patientDTO.get(AbstractDTO.KEY);
            patientDTO.setPatientAttributeValues(
                    getPatientAttributeValueDao().find(key, key));
        }
        return patients;
    }

    public Integer removeSelected(List<String> ids) {
        return getPatientDao().removeSelected(ids);
    }

    public void savePatient(PatientAttributeValue patientAttributeValue, String... columns) {
        getPatientAttributeValueDao().save(patientAttributeValue, columns);
    }
}
