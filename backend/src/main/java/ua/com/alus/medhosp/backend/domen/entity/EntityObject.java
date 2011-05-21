package ua.com.alus.medhosp.backend.domen.entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 02.05.11
 * Time: 1:21
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EntityObject implements Serializable {

    @Id
    @Column(name = "PK_ENTITY_ID")
    private String entityId;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
