package ua.com.alus.medhosp.frontend.client.modules.patients.ui;

import ua.com.alus.medhosp.frontend.client.modules.patients.cto.PatientCTO;
import ua.com.alus.medhosp.frontend.client.resources.locales.patients.PatientConstants;
import ua.com.alus.medhosp.frontend.client.utils.ConstantsBundle;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 02.05.11
 * Time: 12:47
 */
public class PatientsTable extends ListGrid implements ITable {
    private static final String SELECTED = "selected";
    private PatientConstants constants = ConstantsBundle.getInstance().getPsConst();
    private PatientCTO patCTOSample = new PatientCTO();

    public PatientsTable() {
        initTable();
        controller = new PatientsController(this);
    }

    public PatientCTO getPatCTOSample() {
        return patCTOSample;
    }

    private void initTable() {
        ListGridField checkBoxColumn = new ListGridField(SELECTED, "[]");
        checkBoxColumn.setType(ListGridFieldType.BOOLEAN);
        checkBoxColumn.setCanEdit(true);

        checkBoxColumn.addCellSavedHandler(new CellSavedHandler() {
            public void onCellSaved(CellSavedEvent event) {
                Boolean value = (Boolean) event.getNewValue();
                event.getRecord().setAttribute(SELECTED, value);
                if (value) {
                    getSelectedPatients().add(getSelectedRecord().getAttributeAsString(PatientDTO.KEY));
                } else {
                    getSelectedPatients().remove(getSelectedRecord().getAttributeAsString(PatientDTO.KEY));
                }

            }
        });

        ListGridField firstNameColumn = new ListGridField(PatientDTO.NAME, constants.firstName());
        firstNameColumn.setType(ListGridFieldType.TEXT);
        firstNameColumn.setCanEdit(true);
        firstNameColumn.addCellSavedHandler(new CellSavedHandler() {
            public void onCellSaved(CellSavedEvent event) {
                PatientCTO patient = (PatientCTO) event.getRecord();
                patient.setAttribute(PatientDTO.NAME, event.getNewValue());
                getController().savePatient(patCTOSample.convertPersonCTO(patient, new PatientDTO()),
                        PatientDTO.NAME);
            }
        });

        ListGridField lastNameColumn = new ListGridField(PatientDTO.LASTNAME, constants.lastName());
        lastNameColumn.setType(ListGridFieldType.TEXT);
        lastNameColumn.setCanEdit(true);
        lastNameColumn.addCellSavedHandler(new CellSavedHandler() {
            public void onCellSaved(CellSavedEvent event) {
                PatientCTO patient = (PatientCTO) event.getRecord();
                patient.setAttribute(PatientDTO.LASTNAME, event.getNewValue());
                getController().savePatient(patCTOSample.convertPersonCTO(patient, new PatientDTO()),
                        PatientDTO.LASTNAME);
            }
        });

        setFields(checkBoxColumn, lastNameColumn, firstNameColumn);

        setAutoFetchData(true);
        setWidth100();
        setHeight(500);

    }

    private HashSet<String> selectedPatients = new HashSet<String>();

    public HashSet<String> getSelectedPatients() {
        return selectedPatients;
    }

    public void refreshData(PatientCTO[] patientCTOs) {
        getSelectedPatients().clear();
        setData(patientCTOs);
        refreshFields();
    }

    private IController controller;

    public IController getController() {
        return controller;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }
}
