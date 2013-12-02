package ua.com.alus.medhosp.backend.domen.dao.patient;

import ua.com.alus.medhosp.backend.domen.dao.JpaDAO;
import ua.com.alus.medhosp.backend.domen.entity.patient.PatientAttributeValue;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Usatov Alexey
 * Date: 18.05.11
 * Time: 17:19
 */
public class PatientAttributeValueDao extends JpaDAO<String, PatientAttributeValue> {

    @SuppressWarnings("unchecked")
    public List<PatientAttributeValue> getPatientAttributeValue(String entityId, String attributeId) {
        return (List<PatientAttributeValue>) entityManager.createNamedQuery("selectAttributeValue").
                setParameter("entityId", entityId).setParameter("attrId", attributeId).getResultList();
    }

    public void removeAttributeValue(String entityId, String attributeId) {
        Query query = entityManager.createNamedQuery("deleteAttributeValue").
                setParameter("attributeId", attributeId)
                .setParameter("entityId", entityId);
        query.executeUpdate();
        entityManager.flush();
    }
}
