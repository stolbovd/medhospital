package ua.com.alus.medhosp.frontend.server.services.spring;

import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientService;
import ua.com.alus.medhosp.frontend.server.services.spring.dao.PatientAttributeDao;
import ua.com.alus.medhosp.frontend.server.services.spring.dao.PatientAttributeValueDao;
import ua.com.alus.medhosp.frontend.server.services.spring.dao.PatientDao;
import ua.com.alus.medhosp.frontend.shared.AttributeDTO;
import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;

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

    private PatientAttributeDao patientAttributeDao;

    public PatientAttributeDao getPatientAttributeDao() {
        return patientAttributeDao;
    }

    public void setPatientAttributeDao(PatientAttributeDao patientAttributeDao) {
        this.patientAttributeDao = patientAttributeDao;
    }

    public void createPatient(PatientDTO gwtPatient) {
        getPatientDao().save(gwtPatient);
    }

    public List<PatientDTO> getPatients(String entityId) {
        List<PatientDTO> patients = getPatientDao().find(entityId, entityId, new String[]{});
        for (PatientDTO patientDTO : patients) {
            String key = patientDTO.get(BaseColumns.ENTITY_ID.getColumnName());
            patientDTO.setPatientAttributeValues(
                    getPatientAttributeValueDao().find(key, key));
        }
        return patients;
    }

    public void removeSelected(List<String> ids) {
        getPatientDao().removeSelected(ids);
    }

    public void savePatientAttributeValue(PatientAttributeValue patientAttributeValue, String... columns) {
        getPatientAttributeValueDao().save(patientAttributeValue, columns);
    }

    public void savePatientAttribute(AttributeDTO attributeDTO) {
        getPatientAttributeDao().save(attributeDTO);
    }

    public List<AttributeDTO> getAllAttributes() {
        return getPatientAttributeDao().find("", "", new String[]{});
    }
}
