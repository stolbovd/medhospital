package ua.com.alus.medhosp.frontend.server.jms;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import ua.com.alus.medhosp.prototype.data.Constants;
import ua.com.alus.medhosp.prototype.json.CommandsListJson;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 23:51
 */
public class JmsCommandProducer implements ICommandProducer {
    private static final Logger logger = Logger.getLogger(JmsCommandProducer.class);

    private JmsTemplate template = null;

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    public void generateCommands(final CommandsListJson commandsListJson) throws RuntimeException {
        MessageCreator messageCreator = new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                ObjectMapper mapper = new ObjectMapper();
                TextMessage message = session.createTextMessage();
                String command = "";
                try {
                    command = mapper.writeValueAsString(commandsListJson);
                    message.setStringProperty(Constants.COMMAND, command);
                } catch (IOException e) {
                    logger.error("Cannot serialize to json:", e);
                    return null;
                }
                logger.info("Sending message: " + command);
                return message;
            }
        };
        template.send(messageCreator);
    }
}
