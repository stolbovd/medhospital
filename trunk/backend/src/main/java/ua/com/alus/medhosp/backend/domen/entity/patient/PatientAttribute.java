package ua.com.alus.medhosp.backend.domen.entity.patient;

import org.hibernate.engine.Cascade;
import ua.com.alus.medhosp.backend.domen.entity.EntityObject;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "VALUE_TYPE")
    private String valueType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
