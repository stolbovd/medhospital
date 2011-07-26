package ua.com.alus.medhosp.frontend.client.modules.attributes.ui;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import ua.com.alus.medhosp.frontend.client.modules.attributes.cto.AttributeCTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;

/**
 * Table for Attributes
 * <p/>
 * Created by Usatov Alexey
 */
public class AttributesTable extends ListGrid {
    private AttributesController controller;
    private AttributeCTO ctoSample = new AttributeCTO();

    public AttributesTable() {
        initTable();
        controller = new AttributesController(this);
    }

    private void initTable() {
        ListGridField entityIdColumn = new ListGridField(BaseColumns.ENTITY_ID.getColumnName(), "entityId");
        entityIdColumn.setType(ListGridFieldType.TEXT);
        entityIdColumn.setCanEdit(false);

        ListGridField nameColumn = new ListGridField(AttributeColumns.NAME.getColumnName(), "name");
        nameColumn.setType(ListGridFieldType.TEXT);
        nameColumn.setCanEdit(false);

        setFields(entityIdColumn, nameColumn);
        setAutoFetchData(true);
        setWidth100();
        setHeight100();
    }

    public AttributesController getController() {
        return controller;
    }

    public AttributeCTO getCtoSample() {
        return ctoSample;
    }

    public void refreshData(AttributeCTO[] attributeCTOs) {
        setData(attributeCTOs);
        refreshFields();
    }
}
