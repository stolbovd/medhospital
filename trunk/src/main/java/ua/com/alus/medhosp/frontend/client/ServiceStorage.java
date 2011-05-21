package ua.com.alus.medhosp.frontend.client;

import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientService;
import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientServiceAsync;
import com.google.gwt.core.client.GWT;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 01.05.11
 * Time: 23:32
 */
public class ServiceStorage {

    private static ServiceStorage instance = new ServiceStorage();

    public static ServiceStorage getInstance() {
        return instance;
    }

    private IPatientServiceAsync patientServiceAsync;

    public IPatientServiceAsync getPatientServiceAsync() {
        if (patientServiceAsync == null) {
            patientServiceAsync = GWT.create(IPatientService.class);
        }
        return patientServiceAsync;
    }
}
