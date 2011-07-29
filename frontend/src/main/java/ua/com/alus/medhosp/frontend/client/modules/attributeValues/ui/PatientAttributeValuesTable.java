package ua.com.alus.medhosp.frontend.client.modules.attributeValues.ui;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import ua.com.alus.medhosp.frontend.client.modules.attributeValues.cto.AttributeValueCTO;
import ua.com.alus.medhosp.frontend.client.resources.locales.attrValues.AttributeValuesBundle;
import ua.com.alus.medhosp.frontend.client.utils.Bundle;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeValueColumns;

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


    public AttributeValueCTO getCtoSample() {
        return ctoSample;
    }

    public PatientAttributeValuesTable(String entityId) {
        this.entityId = entityId;
        this.controller = new PatientAttributeValueController(this);
        initTable();
    }

    private void initTable() {
        ListGridField attributeName = new ListGridField(AttributeValueColumns.ATTRIBUTE_ID.getColumnName(), bundle.attributeId());
        attributeName.setCanEdit(false);
        attributeName.setType(ListGridFieldType.TEXT);

        ListGridField attributeValue = new ListGridField(AttributeValueColumns.ATTRIBUTE_VALUE.getColumnName(), bundle.attributeValue());
        attributeValue.setCanEdit(false);
        attributeValue.setType(ListGridFieldType.TEXT);

        setFields(attributeName, attributeValue);
        setWidth100();
        setHeight100();
    }

    public String getEntityId() {
        return entityId;
    }

    public void refreshData(AttributeValueCTO[] attributeCTOs) {
        setData(attributeCTOs);
        refreshFields();
    }

    public PatientAttributeValueController getController() {
        return controller;
    }
}
