package ua.com.alus.medhosp.backend.service;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alus.medhosp.backend.domen.dao.patient.PatientAttributeDao;
import ua.com.alus.medhosp.backend.domen.dao.patient.PatientAttributeValueDao;
import ua.com.alus.medhosp.backend.domen.dao.patient.PatientDao;
import ua.com.alus.medhosp.backend.domen.entity.patient.Patient;
import ua.com.alus.medhosp.backend.domen.entity.patient.PatientAttribute;
import ua.com.alus.medhosp.backend.domen.entity.patient.PatientAttributeValue;

import java.util.List;

/**
 * Created by Usatov Alexey
 * Date: 02.05.11
 * Time: 10:49
 */
public class PatientService {

    private PatientDao patientDao;
    private PatientAttributeValueDao patientAttributeValueDao;
    private PatientAttributeDao patientAttributeDao;

    public PatientAttributeDao getPatientAttributeDao() {
        return patientAttributeDao;
    }

    public void setPatientAttributeDao(PatientAttributeDao patientAttributeDao) {
        this.patientAttributeDao = patientAttributeDao;
    }

    public PatientDao getPatientDao() {
        return patientDao;
    }

    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public void setPatientAttributeValueDao(PatientAttributeValueDao patientAttributeValueDao) {
        this.patientAttributeValueDao = patientAttributeValueDao;
    }

    public PatientAttributeValueDao getPatientAttributeValueDao() {
        return patientAttributeValueDao;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void savePatient(Patient gwtPatient) {
        getPatientDao().persist(gwtPatient);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer removeSelected(List<String> ids) {

        return getPatientDao().removeSelected(ids);
    }

    public PatientAttributeValue getPatientAttributeValue(String entityId, String atrtibuteId) {
        List<PatientAttributeValue> result =
                getPatientAttributeValueDao().getPatientAttributeValue(entityId, atrtibuteId);
        if (result.size() == 0) return null;
        return result.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void savePatientAttributeValue(String entityId, String attributeId, String value) {
        PatientAttributeValue patientAttributeValue = getPatientAttributeValue(entityId, attributeId);
        if (patientAttributeValue == null) {
            patientAttributeValue = new PatientAttributeValue();
            patientAttributeValue.setEntityId(entityId);
            patientAttributeValue.setAttributeId(attributeId);
            patientAttributeValue.setPatientAttribute(getPatientAttributeDao().findById(attributeId));
        }
        patientAttributeValue.setValue(value);
        getPatientAttributeValueDao().persist(patientAttributeValue);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void removePatientAttributeValue(String entityId, String attributeId) {
        PatientAttributeValue patientAttributeValue = getPatientAttributeValue(entityId, attributeId);
        if (patientAttributeValue == null) {
            return;
        }
        getPatientAttributeValueDao().remove(patientAttributeValue);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void savePatientAttribute(PatientAttribute patientAttribute) {
        getPatientAttributeDao().persist(patientAttribute);
    }
}
