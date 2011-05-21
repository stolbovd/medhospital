package ua.com.alus.medhosp.backend.jms;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 21.05.11
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
public class JmsEventProducer {

    private static final Logger logger = Logger.getLogger(JmsEventProducer.class);

    private JmsTemplate template = null;

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    /**
     * Generates JMS messages
     *
     * @throws javax.jms.JMSException if error
     */
    public void generateMessages(final String json) throws JMSException {
        MessageCreator messageCreator = new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage();
                message.setStringProperty(JmsCommandListener.COMMAND, json);
                logger.info("Sending message: " + json);
                return message;
            }
        };
        template.send(messageCreator);

    }
}
