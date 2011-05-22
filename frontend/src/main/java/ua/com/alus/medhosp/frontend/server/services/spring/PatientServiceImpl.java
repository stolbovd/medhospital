package ua.com.alus.medhosp.frontend.server.services.spring;

import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientService;
import ua.com.alus.medhosp.frontend.server.services.spring.dao.PatientDao;
import ua.com.alus.medhosp.frontend.shared.AbstractDTO;
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

    public void createPatient(PatientDTO gwtPatient) {
        getPatientDao().save(gwtPatient);
    }

    public List<PatientDTO> getAllPatients() {
        return getPatientDao().findAll();
    }

    public Integer removeSelected(List<String> ids) {
        return getPatientDao().removeSelected(ids);
    }

    public void savePatient(PatientDTO gwtPatient, String... columns) {
        gwtPatient.put(AbstractDTO.KEY, null);
        getPatientDao().save(gwtPatient, columns);
    }
}
