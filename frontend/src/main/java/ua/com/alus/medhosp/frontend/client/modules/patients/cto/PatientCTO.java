package ua.com.alus.medhosp.frontend.client.modules.patients.cto;

import com.google.gwt.core.client.JavaScriptObject;
import ua.com.alus.medhosp.frontend.client.main.domen.ObjectCTO;
import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;
import ua.com.alus.medhosp.prototype.data.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Usatov Alexey
 * Date: 14.05.11
 * Time: 1:13
 */
public class PatientCTO extends ObjectCTO<PatientDTO, PatientCTO> {

    @Override
    @SuppressWarnings("unchecked")
    public PatientCTO convertPersonDTO(PatientDTO abstractDTO, PatientCTO abstractCTO) {
        PatientCTO result = super.convertPersonDTO(abstractDTO, abstractCTO);

        for (PatientAttributeValue patientAttributeValue : abstractDTO.getPatientAttributeValues()) {
            HashMap<String, String> map = new HashMap<String, String>();
            Set<String> keys = patientAttributeValue.keySet();
            for (String key : keys) {
                map.put(key, String.valueOf(patientAttributeValue.get(key)));
            }
            result.setAttribute(patientAttributeValue.getSuperKeyName(), map);
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PatientDTO convertPersonCTO(PatientCTO abstractCTO, PatientDTO abstractDTO) {
        PatientDTO result = super.convertPersonCTO(abstractCTO, abstractDTO);

        String[] attributes = abstractCTO.getAttributes();
        PatientAttributeValue pav;
        for (String attribute : attributes) {
            if (attribute.equals(BaseColumns.ENTITY_ID.getColumnName())) {
                continue;
            }
            Object attrObject;
            if ((attrObject = abstractCTO.getAttributeAsObject(attribute)) instanceof Map) {
                Map<String, String> attrMap = (Map<String, String>) attrObject;
                pav = new PatientAttributeValue();
                Set<String> keys = attrMap.keySet();
                for (String key : keys) {
                    pav.put(key, attrMap.get(key));
                }
                result.getPatientAttributeValues().add(pav);
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public JavaScriptObject getPatAtttribueValue(String attributeName) {
        return (JavaScriptObject) getAttributeAsObject(attributeName);
    }
}
