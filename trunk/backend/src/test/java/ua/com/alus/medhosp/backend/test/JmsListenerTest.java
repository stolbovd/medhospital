package ua.com.alus.medhosp.backend.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import ua.com.alus.medhosp.backend.domen.utils.UUID;
import ua.com.alus.medhosp.backend.test.jms.JmsClientMessageProducer;

import javax.jms.JMSException;

/**
 * Created by IntelliJ IDEA.
 * User: Usatov Alexey
 * Date: 19.05.11
 * Time: 17:04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({})
@ContextConfiguration(locations = {"/spring-config.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class JmsListenerTest extends AbstractTransactionalJUnit4SpringContextTests {

    private Logger logger = Logger.getLogger(JmsListenerTest.class);

    @Autowired
    private JmsClientMessageProducer jmsMessageProducer;

    @Test
    public void sendJmsMessage() {
        String entityId = UUID.uuid();
        String messageId = UUID.uuid();
        try {
            jmsMessageProducer.generateMessages(entityId, messageId);
        } catch (JMSException e) {
            logger.trace(e);
        }
    }
}
