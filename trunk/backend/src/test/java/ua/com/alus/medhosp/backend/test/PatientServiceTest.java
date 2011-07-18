package ua.com.alus.medhosp.backend.test;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import ua.com.alus.medhosp.backend.domen.entity.patient.Patient;
import ua.com.alus.medhosp.backend.domen.entity.patient.PatientAttribute;
import ua.com.alus.medhosp.backend.domen.entity.patient.PatientAttributeValue;
import ua.com.alus.medhosp.backend.service.PatientService;
import ua.com.alus.medhosp.prototype.util.UUID;

/**
 * Created by Usatov Alexey<br/>
 * JUnit test for testing PatientService.
 * <p/>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({})
@ContextConfiguration(locations = {"/spring-config.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class PatientServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private PatientService patientService;

    @Test
    public void createPatientTest() {
        Patient patientEntity = new Patient();
        patientEntity.setEntityId(UUID.uuid());
        patientService.savePatient(patientEntity);
    }

    @Test
    public void createAttributeValueTest() {
        Patient patient = new Patient();
        patient.setEntityId(UUID.uuid());
        patientService.savePatient(patient);

        PatientAttribute patientAttribute = new PatientAttribute();
        patientAttribute.setEntityId(UUID.uuid());
        patientAttribute.setName(UUID.uuid());
        patientService.savePatientAttribute(patientAttribute);


        patientService.savePatientAttributeValue(patient.getEntityId(), patientAttribute.getEntityId(), "Hello");

        PatientAttributeValue patientAttributeValue = patientService.getPatientAttributeValue(
                patient.getEntityId(),
                patientAttribute.getEntityId());

        Assert.assertEquals(patientService.getPatientAttributeValue(
                patient.getEntityId(),
                patientAttribute.getEntityId()).getValue(),
                "Hello"
        );

    }

}
