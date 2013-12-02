package ua.com.alus.medhosp.backend.domen.entity.patient;

import lombok.Getter;
import lombok.Setter;
import ua.com.alus.medhosp.backend.domen.entity.EntityObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Usatov Alexey
 * Date: 18.05.11
 * Time: 16:45
 */
@Entity
@Table(name = "PATIENT_ATTRIBUTE")
public class PatientAttribute extends EntityObject {
    @Setter
    @Getter
    @Column(name = "NAME", nullable = false)
    private String name;
    @Setter
    @Getter
    @Column(name = "TYPE")
    private String type;
    @Setter
    @Getter
    @Column(name = "DEFINITION")
    private String definition;
}
