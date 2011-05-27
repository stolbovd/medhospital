package ua.com.alus.medhosp.frontend.server.jms;

import org.apache.log4j.Logger;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created Usatov Alexey
 * Date: 27.05.11
 * Time: 11:10
 */
public class JmsCommandListener implements MessageListener {
    private Logger logger = Logger.getLogger(JmsCommandListener.class);

    public static final String COMMAND = "command";

    @SuppressWarnings("unchecked")
    public void onMessage(Message message) {
        try {
            logger.info("onMessage in  FRONTEND!"+ message.getStringProperty(COMMAND));
        } catch (Exception e) {
            logger.trace(e);
        }
    }

}
