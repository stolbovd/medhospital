package ua.com.alus.medhosp.backend.test;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.alus.medhosp.backend.service.PatientService;

/**
 * Created by Usatov Alexey
 * Date: 18.05.11
 * Time: 14:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({})
@ContextConfiguration(locations = {"/spring-config.xml"})
public class SpringContextTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private PatientService patientService;

    @Test
    public void testSpringContext() {
        Assert.assertNotNull(patientService);
    }

}
