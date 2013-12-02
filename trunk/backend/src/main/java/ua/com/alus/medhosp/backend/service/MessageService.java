package ua.com.alus.medhosp.backend.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alus.medhosp.backend.domen.dao.message.MessageDao;
import ua.com.alus.medhosp.backend.domen.entity.message.Message;

/**
 * Service for proceed Message entity
 * <p/>
 * Created by Usatov Alexey
 */
public class MessageService {

    @Getter
    @Setter
    private MessageDao messageDao;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveMessage(Message message) {
        getMessageDao().persist(message);
    }

    public Message getMessage(String entityId) {
        return getMessageDao().findById(entityId);
    }
}
