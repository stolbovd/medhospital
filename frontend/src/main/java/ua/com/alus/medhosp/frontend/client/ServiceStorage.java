package ua.com.alus.medhosp.frontend.client;

import com.google.gwt.core.client.GWT;
import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientJmsService;
import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientJmsServiceAsync;
import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientService;
import ua.com.alus.medhosp.frontend.client.modules.patients.rpc.IPatientServiceAsync;
import ua.com.alus.medhosp.frontend.client.modules.tasks.rpc.ITasksService;
import ua.com.alus.medhosp.frontend.client.modules.tasks.rpc.ITasksServiceAsync;

/**
 * Created by Usatov Alexey
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

    private IPatientJmsServiceAsync patientJmsServiceAsync;

    public IPatientJmsServiceAsync getPatientJmsServiceAsync() {
        if (patientJmsServiceAsync == null) {
            patientJmsServiceAsync = GWT.create(IPatientJmsService.class);
        }
        return patientJmsServiceAsync;
    }

    private ITasksServiceAsync tasksServiceAsync;

    public ITasksServiceAsync getTasksServiceAsync() {
        if (tasksServiceAsync == null) {
            tasksServiceAsync = GWT.create(ITasksService.class);
        }
        return tasksServiceAsync;
    }
}
