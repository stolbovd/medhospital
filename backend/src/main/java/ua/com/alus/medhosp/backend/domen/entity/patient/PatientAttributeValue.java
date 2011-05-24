package ua.com.alus.medhosp.backend.domen.entity.patient;

import ua.com.alus.medhosp.backend.domen.entity.EntityAttributeValue;

import javax.persistence.*;

/**
 * Created by Usatov Alexey
 * Date: 18.05.11
 * Time: 17:14
 */
@Entity
@Table(name = "PATIENT_ATTRIBUTE_VALUE")
@NamedQueries(value = {
        @NamedQuery(name = "selectAttributeValue", query = "SELECT p FROM PatientAttributeValue p WHERE p.entityId=:entityId and p.attributeId=:attrId")
})
public class PatientAttributeValue extends EntityAttributeValue {

    @ManyToOne
    @JoinColumn(name = "FK_ATTRIBUTE_ID")
    private PatientAttribute patientAttribute;


    public PatientAttribute getPatientAttribute() {
        return patientAttribute;
    }

    public void setPatientAttribute(PatientAttribute patientAttribute) {
        this.patientAttribute = patientAttribute;
    }
}
