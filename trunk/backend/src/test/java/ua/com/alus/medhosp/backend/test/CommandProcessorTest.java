package ua.com.alus.medhosp.backend.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import ua.com.alus.medhosp.backend.domen.utils.UUID;
import ua.com.alus.medhosp.backend.processor.CommandProcessorImpl;

/**
 * Created by IntelliJ IDEA.
 * User: Usatov Alexey
 * Date: 19.05.11
 * Time: 15:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({})
@ContextConfiguration(locations = {"/spring-config.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class CommandProcessorTest extends AbstractTransactionalJUnit4SpringContextTests {

    private String savePatientJson = "{\"commands\":[\n" +
            " {\n" +
            "  \"command\":\"SavePatientCommand\",\n" +
            "   \"properties\":{\n" +
            "         \"entityId\":\"" + UUID.uuid() + "\"   }\n" +
            "  }\n" +
            " ]}";

    @Autowired
    private CommandProcessorImpl commandProcessor;

    @Test
    public void savePatientCommand() {
        commandProcessor.processCommand(savePatientJson);
    }
}
