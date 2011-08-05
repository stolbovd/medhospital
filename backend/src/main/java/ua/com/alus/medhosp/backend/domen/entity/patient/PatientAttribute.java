package ua.com.alus.medhosp.backend.domen.entity.patient;

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

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "DEFINITION")
    private String definition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
