package ua.com.alus.medhosp.backend.test;

import org.axonframework.commandhandling.CommandBus;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import ua.com.alus.medhosp.backend.axon.api.patient.command.RemovePatientCommand;
import ua.com.alus.medhosp.backend.axon.api.patient.command.SaveAttributeValueCommand;
import ua.com.alus.medhosp.backend.axon.api.patient.command.SavePatientCommand;
import ua.com.alus.medhosp.backend.domen.utils.UUID;
import ua.com.alus.medhosp.backend.service.PatientService;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 12:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({})
@ContextConfiguration(locations = {"/spring-config.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class PatientAxonSpringTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private CommandBus commandBus;

    @Autowired
    private PatientService patientService;

    @Test
    public void savePatient() {
        String uuid = UUID.uuid();
        commandBus.dispatch(new SavePatientCommand(uuid, null));
    }

    @Test
    public void removePatient() {
        String uuid = UUID.uuid();
        commandBus.dispatch(new SavePatientCommand(uuid, null));
        commandBus.dispatch(new RemovePatientCommand(uuid, null));
    }

    //@Ignore
    @Test
    public void saveAttributeValue(){
        String entityId = "12C6A3D7-5EDD-4508-AD3D-C0CBAE131ABD";
        String attributeId = "F9C308D7-8629-46F6-89DA-990DBE1E4CA8";
        String value = "hello1";

        //commandBus.dispatch(new SavePatientCommand(entityId,null));
        commandBus.dispatch(new SaveAttributeValueCommand(entityId,null,attributeId, value));
    }

}
