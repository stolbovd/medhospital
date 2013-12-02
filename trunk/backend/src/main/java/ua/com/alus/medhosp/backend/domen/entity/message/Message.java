package ua.com.alus.medhosp.backend.domen.entity.message;

import lombok.Getter;
import lombok.Setter;
import ua.com.alus.medhosp.backend.domen.entity.EntityObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity Message (representation of message that were sent to client)
 * <p/>
 * Created by Usatov Alexey
 */
@Entity
@Table(name = "MESSAGE")
public class Message extends EntityObject {
    @Column(name = "JSON")
    @Setter
    @Getter
    private String json;
}
