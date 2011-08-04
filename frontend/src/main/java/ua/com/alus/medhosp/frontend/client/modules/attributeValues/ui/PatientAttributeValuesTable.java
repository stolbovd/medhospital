package ua.com.alus.medhosp.frontend.client.modules.attributeValues.ui;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import ua.com.alus.medhosp.frontend.client.modules.attributeValues.cto.AttributeValueCTO;
import ua.com.alus.medhosp.frontend.client.resources.locales.attrValues.AttributeValuesBundle;
import ua.com.alus.medhosp.frontend.client.utils.Bundle;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeValueColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;

import java.util.HashSet;

/**
 * Table with attribute values for specified patient
 * <p/>
 * Created by Usatov Alexey
 */
public class PatientAttributeValuesTable extends ListGrid {
    private PatientAttributeValueController controller;
    private String entityId;
    private AttributeValueCTO ctoSample = new AttributeValueCTO();
    private AttributeValuesBundle bundle = Bundle.getInstance().getAttributeValuesBundle();
    private static final String SELECTED = "selected";

    public AttributeValueCTO getCtoSample() {
        return ctoSample;
    }

    public PatientAttributeValuesTable(String entityId) {
        this.entityId = entityId;
        this.controller = new PatientAttributeValueController(this);
        initTable();
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
                    getSelectedAttributeValues().add(getSelectedRecord());
                } else {
                    getSelectedAttributeValues().remove(getSelectedRecord());
                }

            }
        });

        ListGridField attributeName = new ListGridField(AttributeValueColumns.ATTRIBUTE_ID.getColumnName(), bundle.attributeId());
        attributeName.setCanEdit(false);
        attributeName.setType(ListGridFieldType.TEXT);

        ListGridField attributeValue = new ListGridField(AttributeValueColumns.ATTRIBUTE_VALUE.getColumnName(), bundle.attributeValue());
        attributeValue.setCanEdit(false);
        attributeValue.setType(ListGridFieldType.TEXT);

        setFields(checkBoxColumn, attributeName, attributeValue);
        setWidth100();
        setHeight100();
    }

    public String getEntityId() {
        return entityId;
    }

    public void refreshData(AttributeValueCTO[] attributeCTOs) {
        getSelectedAttributeValues().clear();
        setData(attributeCTOs);
        refreshFields();
    }

    public PatientAttributeValueController getController() {
        return controller;
    }

    private HashSet<ListGridRecord> selectedAttributeValues = new HashSet<ListGridRecord>();

    public HashSet<ListGridRecord> getSelectedAttributeValues() {
        return selectedAttributeValues;
    }
}
