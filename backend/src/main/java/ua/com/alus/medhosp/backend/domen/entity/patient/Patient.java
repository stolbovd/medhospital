package ua.com.alus.medhosp.backend.domen.entity.patient;

import ua.com.alus.medhosp.backend.domen.entity.EntityObject;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Created by Usatov Alexey
 * Date: 18.05.11
 * Time: 16:38
 */
@Entity
@Table(name = "PATIENT")

@NamedQueries(value = {
        @NamedQuery(name = "removeValuesForAttributes", query = "DELETE from PatientAttributeValue p WHERE p.entityId in (:list)"),
        @NamedQuery(name = "removePatient", query = "DELETE from Patient p WHERE p.entityId in (:list)")
})
public class Patient extends EntityObject {

}
