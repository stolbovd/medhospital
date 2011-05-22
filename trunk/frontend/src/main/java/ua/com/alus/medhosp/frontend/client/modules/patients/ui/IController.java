package ua.com.alus.medhosp.frontend.client.modules.patients.ui;


import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;

/**
 * Created by Usatov Alexey
 * Date: 03.05.11
 * Time: 11:26
 */
public interface IController {
    void removeSelected();

    void refreshTable();

    void savePatient(PatientAttributeValue patientDTO, String... column);
}
