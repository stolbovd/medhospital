package ua.com.alus.medhosp.backend.domen.dao.patient;

import ua.com.alus.medhosp.backend.domen.dao.JpaDAO;
import ua.com.alus.medhosp.backend.domen.entity.patient.PatientAttributeValue;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Usatov Alexey
 * Date: 18.05.11
 * Time: 17:19
 */
public class PatientAttributeValueDao extends JpaDAO<String, PatientAttributeValue> {

    @SuppressWarnings("unchecked")
    public List<PatientAttributeValue> getPatientAttributeValue(String entityId, String attributeId) {
        return (List<PatientAttributeValue>) entityManager.createNamedQuery("selectAttributeValue").
                setParameter("entityId", entityId).setParameter("attrId", attributeId).getResultList();
    }
}
