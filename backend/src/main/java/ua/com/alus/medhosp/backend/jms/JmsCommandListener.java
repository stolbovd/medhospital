package ua.com.alus.medhosp.backend.jms;


import org.apache.log4j.Logger;
import ua.com.alus.medhosp.backend.processor.ICommandProcessor;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * This is listener for JMS messages from client application that contain command and its properties.
 * Created by IntelliJ IDEA.
 * User: Usatov Alexey
 * Date: 19.05.11
 * Time: 14:48
 */
public class JmsCommandListener implements MessageListener {
    private Logger logger = Logger.getLogger(JmsCommandListener.class);

    public final static String COMMAND = "command";

    private ICommandProcessor commandProcessor;

    public void setCommandProcessor(ICommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onMessage(Message message) {
        try {
            commandProcessor.processCommand(message.getStringProperty(COMMAND));
        } catch (Exception e) {
            logger.trace(e);
        }
    }
}
