package ua.com.alus.medhosp.backend.test;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
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
import ua.com.alus.medhosp.prototype.util.UUID;

/**
 * Created by Usatov Alexey
 * Date: 19.05.11
 * Time: 12:18
 */
//TODO Some tests fall because in commandhandlers I'm trying to load Aggregate every time, ant if it throws exception AggregateNotFoundException
//TODO I add Aggregate to repository. In this case I don't need to separate save() and create() methods
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

    @Ignore
    @Test
    public void savePatient() {
        String uuid = UUID.uuid();
        fixture.given()
                .when(new SavePatientCommand(uuid, "",""))
                .expectEvents(new SavePatientEvent(uuid, "",""));
    }

    @Test
    public void removePatient() {
        String uuid = UUID.uuid();
        fixture.given(new SavePatientEvent(uuid, "",""))
                .when(new RemovePatientCommand(fixture.getAggregateIdentifier().asString(), "",""))
                .expectEvents(new RemovePatientEvent(fixture.getAggregateIdentifier().asString(), "",""));
    }

    @Test
    public void saveAttributeValue() {
        String entityId = UUID.uuid();
        String attributeId = UUID.uuid();
        String attributeValue = "value";
        fixture.given().when(new SaveAttributeValueCommand(entityId, "","", attributeId, attributeValue))
                .expectEvents(new SaveAttributeValueEvent(entityId, "","", attributeId, attributeValue));
    }

    @Test
    public void removeAttributeValue() {
        String entityId = UUID.uuid();
        String attributeId = UUID.uuid();
        fixture.given().when(new SaveAttributeValueCommand(entityId, "","", attributeId, ""))
                .expectEvents(new SaveAttributeValueEvent(entityId, "", "", attributeId, ""));
        fixture.given().when(new RemoveAttributeValueCommand(entityId, "","", attributeId))
                .expectEvents(new RemoveAttributeValueEvent(entityId, "","", attributeId));
    }
}
