package ua.com.alus.medhosp.backend.domen.entity.patient;

import ua.com.alus.medhosp.backend.domen.entity.EntityAttributeValue;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Created by Usatov Alexey
 * Date: 18.05.11
 * Time: 17:14
 */
@Entity
@Table(name = "PATIENT_ATTRIBUTE_VALUE")
@NamedQueries(value = {
        @NamedQuery(name = "selectAttributeValue", query = "SELECT p FROM PatientAttributeValue p WHERE p.entityId=:entityId and p.attributeId=:attrId"),
        @NamedQuery(name = "deleteAttributeValue", query = "delete from PatientAttributeValue p " +
                "where p.attributeId=:attributeId and p.entityId=:entityId")
})
public class PatientAttributeValue extends EntityAttributeValue {


}
