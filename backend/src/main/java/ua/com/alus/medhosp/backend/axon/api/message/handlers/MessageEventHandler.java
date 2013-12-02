package ua.com.alus.medhosp.backend.axon.api.message.handlers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.axonframework.eventhandling.annotation.EventHandler;
import ua.com.alus.medhosp.backend.axon.api.message.event.ReSendMessageEvent;
import ua.com.alus.medhosp.backend.domen.entity.message.Message;
import ua.com.alus.medhosp.backend.jms.IJmsEventProducer;
import ua.com.alus.medhosp.backend.service.MessageService;

/**
 * Message Event Handler
 * <p/>
 * Created by Usatov Alexey
 */
public class MessageEventHandler {
    private Logger logger = Logger.getLogger(MessageEventHandler.class);

    @Setter @Getter private MessageService messageService;

    @Setter @Getter IJmsEventProducer jmsEventProducer;

    @EventHandler
    public void resendMessageEventHandlerJMS(ReSendMessageEvent event) {
        Message message = getMessageService().getMessage(event.getEntityId());
        getJmsEventProducer().sendJson(message.getJson());
    }
}
