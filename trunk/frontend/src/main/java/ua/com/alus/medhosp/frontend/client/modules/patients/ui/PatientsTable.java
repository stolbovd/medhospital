package ua.com.alus.medhosp.frontend.client.modules.patients.ui;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import ua.com.alus.medhosp.frontend.client.modules.patients.cto.PatientCTO;
import ua.com.alus.medhosp.frontend.client.resources.locales.patients.PatientConstants;
import ua.com.alus.medhosp.frontend.client.utils.ConstantsBundle;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;

import java.util.HashSet;

/**
 * Created by Usatov Alexey
 * Date: 02.05.11
 * Time: 12:47
 */
public class PatientsTable extends ListGrid implements ITable {
    private static final String SELECTED = "selected";
    private PatientConstants constants = ConstantsBundle.getInstance().getPsConst();
    private PatientCTO patCTOSample = new PatientCTO();

    public static final String NAME_ATTRIBUTE = "name";
    public static final String LAST_NAME_ATTRIBUTE = "lastName";

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
                    getSelectedPatients().add(getSelectedRecord().getAttributeAsString(BaseColumns.ENTITY_ID.getColumnName()));
                } else {
                    getSelectedPatients().remove(getSelectedRecord().getAttributeAsString(BaseColumns.ENTITY_ID.getColumnName()));
                }

            }
        });

        SimpleField entityIdColumn = new SimpleField(BaseColumns.ENTITY_ID.getColumnName(), constants.id());
        entityIdColumn.setType(ListGridFieldType.TEXT);
        entityIdColumn.setCanEdit(false);

        setFields(checkBoxColumn, entityIdColumn);

        setAutoFetchData(true);
        setWidth100();
        setHeight100();

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
