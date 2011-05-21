package ua.com.alus.medhosp.backend.test.jms;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import ua.com.alus.medhosp.backend.jms.JmsCommandListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by IntelliJ IDEA.
 * User: Usatov Alexey
 * Date: 19.05.11
 * Time: 17:00
 */
public class JmsClientMessageProducer {

    private static final Logger logger = Logger.getLogger(JmsClientMessageProducer.class);

    private JmsTemplate template = null;

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    private String savePatientJson = "{\"commands\":[\n" +
            " {\n" +
            "  \"command\":\"SavePatientCommand\",\n" +
            "   \"properties\":{\n" +
            "         \"entityId\":\"" + "%s" + "\"," +
            "          \"messageId\":\"" + "%s" + "\"  }\n" +
            "  }\n" +
            " ]}";

    /**
     * Generates JMS messages
     *
     * @param entity    entityId
     * @param messageId messageId
     * @throws javax.jms.JMSException if error
     */
    public void generateMessages(String entity, String messageId) throws JMSException {

        final String commandJson = String.format(savePatientJson, entity, messageId);
        MessageCreator messageCreator = new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage();
                message.setStringProperty(JmsCommandListener.COMMAND, commandJson);
                logger.info("Sending message: " + commandJson);
                return message;
            }
        };
        template.send(messageCreator);

    }
}
