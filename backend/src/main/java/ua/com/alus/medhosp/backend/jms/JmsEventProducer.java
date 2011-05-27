package ua.com.alus.medhosp.backend.jms;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

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

    /**
     * Generates JMS messages
     *
     * @throws javax.jms.JMSException if error
     */
    public void sendResult(final Map<String, String> map) {
        try {
            MessageCreator messageCreator = new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    TextMessage message = session.createTextMessage();
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        String json = mapper.writeValueAsString(map);

                        message.setStringProperty(JmsCommandListener.COMMAND, json);
                        logger.info("Sending message: " + json);
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                    return message;
                }
            };
            template.send(messageCreator);
        } catch (JmsException jmse) {
            logger.error(jmse);
        }
    }
}
