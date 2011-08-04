package ua.com.alus.medhosp.frontend.client.modules.attributeValues.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import ua.com.alus.medhosp.frontend.client.ServiceStorage;
import ua.com.alus.medhosp.frontend.client.modules.attributeValues.cto.AttributeValueCTO;
import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeValueColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for PatientAttributeValue
 * <p/>
 * Created by Usatov Alexey
 */
public class PatientAttributeValueController {

    private PatientAttributeValuesTable patientAttributeValuesTable;

    public PatientAttributeValueController(PatientAttributeValuesTable table) {
        this.patientAttributeValuesTable = table;
    }

    public PatientAttributeValuesTable getPatientAttributeValuesTable() {
        return patientAttributeValuesTable;
    }

    public void setPatientAttributeValuesTable(PatientAttributeValuesTable patientAttributeValuesTable) {
        this.patientAttributeValuesTable = patientAttributeValuesTable;
    }

    public void refreshTable() {
        ServiceStorage.getInstance().getPatientServiceAsync().getAttributeValuesByPatientId(getPatientAttributeValuesTable().getEntityId(), new AsyncCallback<List<PatientAttributeValue>>() {
            public void onFailure(Throwable caught) {
                SC.say("Error:" + caught);
            }

            public void onSuccess(List<PatientAttributeValue> result) {
                AttributeValueCTO[] attributeCTOs = new AttributeValueCTO[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    attributeCTOs[i] = getPatientAttributeValuesTable().getCtoSample().convertPersonDTO(result.get(i), new AttributeValueCTO());
                }
                getPatientAttributeValuesTable().refreshData(attributeCTOs);
            }
        });
    }

    public void removeSelected() {
        List<String> attrIds = new ArrayList<String>();
        for (ListGridRecord record : getPatientAttributeValuesTable().getSelectedAttributeValues()) {
            attrIds.add(record.getAttributeAsString(AttributeValueColumns.ATTRIBUTE_ID.getColumnName()));
        }
        ServiceStorage.getInstance().getPatientJmsServiceAsync().removeSelectedAttrValues(getPatientAttributeValuesTable().getEntityId(), attrIds, new AsyncCallback<Void>() {
            public void onFailure(Throwable throwable) {
                SC.say("Error:" + throwable);
            }

            public void onSuccess(Void aVoid) {
                getPatientAttributeValuesTable().getSelectedAttributeValues().clear();
                SC.say("Success");
            }
        });
    }
}
