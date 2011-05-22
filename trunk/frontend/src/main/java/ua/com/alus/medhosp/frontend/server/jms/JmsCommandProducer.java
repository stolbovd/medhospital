package ua.com.alus.medhosp.frontend.server.jms;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 23:51
 */
public class JmsCommandProducer {
    private static final Logger logger = Logger.getLogger(JmsCommandProducer.class);

    private JmsTemplate template = null;

    public static final String COMMAND = "command";

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    public void generateMessages() throws JMSException {
        MessageCreator messageCreator = new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage();
                message.setStringProperty(COMMAND, "");
                logger.info("Sending message: " + "");
                return message;
            }
        };
        template.send(messageCreator);
    }
}
