package ua.com.alus.medhosp.backend.test;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAttributeValueAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.command.RemoveAttributeValueCommand;
import ua.com.alus.medhosp.backend.axon.api.patient.command.RemovePatientCommand;
import ua.com.alus.medhosp.backend.axon.api.patient.command.SaveAttributeValueCommand;
import ua.com.alus.medhosp.backend.axon.api.patient.command.SavePatientCommand;
import ua.com.alus.medhosp.backend.axon.api.patient.event.RemoveAttributeValueEvent;
import ua.com.alus.medhosp.backend.axon.api.patient.event.RemovePatientEvent;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SaveAttributeValueEvent;
import ua.com.alus.medhosp.backend.axon.api.patient.event.SavePatientEvent;
import ua.com.alus.medhosp.backend.axon.api.patient.handlers.AttributeValueCommandHandler;
import ua.com.alus.medhosp.backend.axon.api.patient.handlers.PatientCommandHandler;
import ua.com.alus.medhosp.backend.domen.utils.UUID;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 12:18
 */
//TODO check if we need to separate creating and savving of Aggregate roots
@Ignore
public class PatientAxonTest {
    private FixtureConfiguration fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture();
        PatientCommandHandler commandHandler = new PatientCommandHandler();
        commandHandler.setRepository(fixture.createGenericRepository(PatientAggregate.class));
        fixture.registerAnnotatedCommandHandler(commandHandler);

        AttributeValueCommandHandler atrValueCommandHandler = new AttributeValueCommandHandler();
        atrValueCommandHandler.setRepository(fixture.createGenericRepository(PatientAttributeValueAggregate.class));
        fixture.registerAnnotatedCommandHandler(atrValueCommandHandler);
    }

    @Test
    public void savePatient() {
        String uuid = UUID.uuid();
        fixture.given()
                .when(new SavePatientCommand(uuid, ""))
                .expectEvents(new SavePatientEvent(fixture.getAggregateIdentifier().asString(), ""));
    }

    @Test
    public void removePatient() {
        String uuid = UUID.uuid();
        fixture.given(new SavePatientEvent(uuid, ""))
                .when(new RemovePatientCommand(fixture.getAggregateIdentifier().asString(), null))
                .expectEvents(new RemovePatientEvent(fixture.getAggregateIdentifier().asString(), null));
    }

    @Test
    public void saveAttributeValue() {
        String entityId = UUID.uuid();
        String attributeId = UUID.uuid();
        String attributeValue = "value";
        fixture.given().when(new SaveAttributeValueCommand(entityId, "", attributeId, attributeValue))
                .expectEvents(new SaveAttributeValueEvent(entityId, "", attributeId, attributeValue));
    }

    @Test
    public void removeAttributeValue() {
        String entityId = UUID.uuid();
        String attributeId = UUID.uuid();
        fixture.given().when(new SaveAttributeValueCommand(entityId, "", attributeId, ""))
                .expectEvents(new SaveAttributeValueEvent(entityId, "", attributeId, ""));
        fixture.given().when(new RemoveAttributeValueCommand(entityId, "", attributeId))
                .expectEvents(new RemoveAttributeValueEvent(entityId, "", attributeId));
    }
}
