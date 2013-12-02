package ua.com.alus.medhosp.backend.domen.entity;


import lombok.Getter;
import lombok.Setter;

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
    @Column(name = "FK_ATTRIBUTE_ID")
    @Setter
    @Getter
    private String attributeId;

    @Column(name = "FK_ENTITY_ID")
    @Setter
    @Getter
    private String entityId;

    @Column(name = "VALUE")
    @Setter
    @Getter
    private String value;
}
