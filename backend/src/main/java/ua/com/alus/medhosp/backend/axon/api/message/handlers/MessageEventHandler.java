package ua.com.alus.medhosp.backend.axon.api.message.handlers;

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

    private MessageService messageService;

    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    IJmsEventProducer jmsEventProducer;

    public IJmsEventProducer getJmsEventProducer() {
        return jmsEventProducer;
    }

    public void setJmsEventProducer(IJmsEventProducer jmsEventProducer) {
        this.jmsEventProducer = jmsEventProducer;
    }

    @EventHandler
    public void resendMessageEventHandlerJMS(ReSendMessageEvent event) {
        Message message = getMessageService().getMessage(event.getEntityId());
        getJmsEventProducer().sendJson(message.getJson());
    }
}
