package ua.com.alus.medhosp.backend.domen.entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Usatov Alexey
 * Date: 18.05.11
 * Time: 16:57
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EntityAttributeValue implements Serializable {

    @Id
    @Column(name = "FK_ENTITY_ID")
    private String entityId;

    @Column(name = "FK_ATTRIBUTE_ID")
    private String attributeId;

    @Column(name = "VALUE")
    private String value;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }
}
