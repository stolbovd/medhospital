package ua.com.alus.medhosp.backend.test;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.PatientAttributeValueAggregate;
import ua.com.alus.medhosp.backend.axon.api.patient.command.RemovePatientCommand;
import ua.com.alus.medhosp.backend.axon.api.patient.command.SaveAttributeValueCommand;
import ua.com.alus.medhosp.backend.axon.api.patient.command.SavePatientCommand;
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
                .when(new SavePatientCommand(uuid, null))
                .expectEvents(new SavePatientEvent(uuid, null));
    }

    @Test
    public void removePatient() {
        String uuid = UUID.uuid();
        fixture.given(new SavePatientEvent(uuid, ""))
                .when(new RemovePatientCommand(fixture.getAggregateIdentifier().asString(), null))
                .expectEvents(new RemovePatientEvent(fixture.getAggregateIdentifier().asString(), null));
    }

    @Test
    public void saveAttributeValue(){
        String entityId = UUID.uuid();
        String attributeId = UUID.uuid();
        String attributeValue = "value";
        fixture.given().when(new SaveAttributeValueCommand(entityId,"",attributeId,attributeValue))
                .expectEvents(new SaveAttributeValueEvent(entityId, "", attributeId, attributeValue));

    }
}
