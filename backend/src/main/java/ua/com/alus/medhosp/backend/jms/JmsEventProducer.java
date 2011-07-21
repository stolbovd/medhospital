package ua.com.alus.medhosp.backend.jms;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import ua.com.alus.medhosp.backend.service.MessageService;
import ua.com.alus.medhosp.prototype.data.Constants;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Map;

/**
 * Created by Usatov Alexey.
 * Date: 21.05.11
 * Time: 14:00
 */
public class JmsEventProducer implements IJmsEventProducer {

    private static final Logger logger = Logger.getLogger(JmsEventProducer.class);

    private JmsTemplate template = null;

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    private MessageService messageService;

    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    private ObjectMapper jsonMapper;

    public ObjectMapper getJsonMapper() {
        if (jsonMapper == null) {
            jsonMapper = new ObjectMapper();
        }
        return jsonMapper;
    }

    /**
     * Generates JMS messages
     *
     * @throws org.springframework.jms if error
     */
    public void sendResult(final Map<String, String> map) throws JmsException {
        try {
            final String json = getJsonMapper().writeValueAsString(map);
            saveMessage(map, json);
            sendJson(json);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    public void sendJson(final String json) throws JmsException {
        try {
            MessageCreator messageCreator = new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    TextMessage message = session.createTextMessage();
                    message.setStringProperty(Constants.COMMAND, json);
                    logger.info("Sending message: " + json);
                    return message;
                }
            };
            template.send(messageCreator);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private void saveMessage(Map<String, String> map, String json) {
        ua.com.alus.medhosp.backend.domen.entity.message.Message message = new ua.com.alus.medhosp.backend.domen.entity.message.Message();
        message.setEntityId(map.get(Constants.MESSAGE_ID));
        message.setJson(json);
        getMessageService().saveMessage(message);
    }
}
