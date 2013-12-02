package ua.com.alus.medhosp.backend.domen.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Usatov Alexey
 * Date: 02.05.11
 * Time: 1:21
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EntityObject implements Serializable {

    @Id
    @Column(name = "PK_ENTITY_ID")
    @Setter
    @Getter
    private String entityId;
}
