package ua.com.alus.medhosp.frontend.client.modules.patients.ui;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import ua.com.alus.medhosp.frontend.client.modules.patients.cto.PatientCTO;
import ua.com.alus.medhosp.frontend.client.resources.images.Icons;
import ua.com.alus.medhosp.frontend.client.resources.locales.patients.PatientBundle;
import ua.com.alus.medhosp.frontend.client.utils.Bundle;
import ua.com.alus.medhosp.frontend.client.utils.Constants;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;

import java.util.HashSet;

/**
 * Created by Usatov Alexey
 * Date: 02.05.11
 * Time: 12:47
 */
public class PatientsTable extends ListGrid implements ITable {
    private static final String SELECTED = "selected";
    private PatientBundle bundle = Bundle.getInstance().getPsBundle();
    private Icons icons = Constants.getInstance().getIcons();
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
                    getSelectedPatients().add(getSelectedRecord());
                } else {
                    getSelectedPatients().remove(getSelectedRecord());
                }

            }
        });

        SimpleField entityIdColumn = new SimpleField(BaseColumns.ENTITY_ID.getColumnName(), bundle.id());
        entityIdColumn.setType(ListGridFieldType.TEXT);
        entityIdColumn.setCanEdit(false);
        entityIdColumn.setValueIconSize(30);
        setFields(checkBoxColumn, entityIdColumn);
        setCellHeight(30);
        setAutoFetchData(true);
        setWidth100();
        setHeight100();
    }

    @Override
    public String getValueIcon(ListGridField field, Object value, ListGridRecord record) {
        if (BaseColumns.ENTITY_ID.getColumnName().equals(field.getName())) {
            return icons.patient();
        }
        return super.getValueIcon(field, value, record);
    }

    private HashSet<ListGridRecord> selectedPatients = new HashSet<ListGridRecord>();

    public HashSet<ListGridRecord> getSelectedPatients() {
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
