package ua.com.alus.medhosp.backend.domen.dao.patient;

import ua.com.alus.medhosp.backend.domen.dao.JpaDAO;
import ua.com.alus.medhosp.backend.domen.entity.patient.Patient;
import ua.com.alus.medhosp.backend.domen.utils.QueryHelper;

import java.util.List;

/**
 * Created by Usatov Alexey
 * Date: 02.05.11
 * Time: 10:46
 */

public class PatientDao extends JpaDAO<String, Patient> {

    public Integer removeSelected(final List<String> ids) {
        String list = QueryHelper.listToString(ids);
        entityManager.createNamedQuery("removeValuesForAttributes").setParameter("list", list).executeUpdate();
        entityManager.createNamedQuery("removePatient").setParameter(
                "list",
                QueryHelper.listToString(ids)).executeUpdate();
        return ids.size();
    }

}
