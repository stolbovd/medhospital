package ua.com.alus.medhosp.frontend.client.modules.attributes.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import ua.com.alus.medhosp.frontend.client.ServiceStorage;
import ua.com.alus.medhosp.frontend.client.modules.attributes.cto.AttributeCTO;
import ua.com.alus.medhosp.frontend.shared.AttributeDTO;

import java.util.List;

/**
 * Controller for attributes
 * <p/>
 * Created by Usatov Alexey
 */
public class AttributesController {
    private AttributesTable attributesTable;

    public AttributesTable getAttributesTable() {
        return attributesTable;
    }

    public AttributesController(AttributesTable attributesTable) {
        this.attributesTable = attributesTable;
    }

    public void refreshTable() {
        ServiceStorage.getInstance().getPatientServiceAsync().getAllAttributes(new AsyncCallback<List<AttributeDTO>>() {
            public void onFailure(Throwable caught) {
                SC.say("Error:" + caught);
            }

            public void onSuccess(List<AttributeDTO> result) {
                AttributeCTO[] attributeCTOs = new AttributeCTO[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    attributeCTOs[i] = getAttributesTable().getCtoSample().convertPersonDTO(result.get(i), new AttributeCTO());
                }
                getAttributesTable().refreshData(attributeCTOs);
            }
        });
    }

    public void saveAttribute(AttributeDTO attributeDTO) {
        ServiceStorage.getInstance().getPatientJmsServiceAsync().saveAttribute(attributeDTO, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
                SC.say("Error:" + caught);
            }

            public void onSuccess(Void result) {
                refreshTable();
            }
        });
    }
}
