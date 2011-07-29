package ua.com.alus.medhosp.frontend.client.modules.attributeValues.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import ua.com.alus.medhosp.frontend.client.ServiceStorage;
import ua.com.alus.medhosp.frontend.client.resources.locales.patients.PatientBundle;
import ua.com.alus.medhosp.frontend.client.utils.Bundle;
import ua.com.alus.medhosp.frontend.shared.AttributeDTO;
import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeValueColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Dialog for view patient attribute values
 * <p/>
 * Created by Usatov Alexey
 */
public class PatientAttributeValueDialog extends Window {
    PatientBundle bundle = Bundle.getInstance().getPsBundle();
    private VLayout mainPanel;
    private String patientId;

    public PatientAttributeValueDialog(String entityId) {
        this.patientId = entityId;
        initDialog();
        ServiceStorage.getInstance().getPatientServiceAsync().getAttributeValuesByPatientId(entityId, new AsyncCallback<List<PatientAttributeValue>>() {
            public void onFailure(Throwable throwable) {
                SC.say("Error:" + throwable);
            }

            public void onSuccess(List<PatientAttributeValue> patientAttributeValues) {
                SC.say("Size:" + patientAttributeValues.size());
            }
        });
    }

    private void initDialog() {
        addItem(getMainPanel());
        setWidth(700);
        setHeight(700);
        centerInPage();
        retrieveData();
    }

    private HLayout addAttributeValuePanel;

    private HLayout getAddAttributeValuePanel() {
        if (addAttributeValuePanel == null) {
            addAttributeValuePanel = new HLayout();
            addAttributeValuePanel.setWidth100();
            addAttributeValuePanel.setHeight(300);

            DynamicForm addAttrValueForm = new DynamicForm();
            addAttrValueForm.setFields(getAttributesCombobox(), getValueField());
            addAttrValueForm.setWidth(400);
            addAttrValueForm.setNumCols(2);
            addAttributeValuePanel.setMembers(addAttrValueForm, getSaveButton());
        }
        return addAttributeValuePanel;
    }


    private TextItem valueField;

    private TextItem getValueField() {
        if (valueField == null) {
            valueField = new TextItem();
            valueField.setTitle("");
        }
        return valueField;
    }

    private void retrieveData() {
        ServiceStorage.getInstance().getPatientServiceAsync().getAllAttributes(new AsyncCallback<List<AttributeDTO>>() {
            public void onFailure(Throwable throwable) {
                SC.say("Error:" + throwable);
            }

            public void onSuccess(List<AttributeDTO> attributeDTOs) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                for (AttributeDTO attributeDTO : attributeDTOs) {
                    map.put(attributeDTO.get(AttributeColumns.ENTITY_ID.getColumnName()), attributeDTO.get(AttributeColumns.NAME.getColumnName()));
                }
                getAttributesCombobox().setValueMap(map);
            }
        });
    }

    private SelectItem attributesCombobox;

    private SelectItem getAttributesCombobox() {
        if (attributesCombobox == null) {
            attributesCombobox = new SelectItem();
            attributesCombobox.setTitle(bundle.searchByAttribute());
            attributesCombobox.setType("comboBox");
            attributesCombobox.setWidth(150);
        }
        return attributesCombobox;
    }

    private VLayout getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new VLayout();
            mainPanel.setMembers(getAddAttributeValuePanel());
        }
        return mainPanel;
    }

    private IButton saveButton;

    private IButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new IButton();
            saveButton.setTitle(bundle.savePatient());
            saveButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent clickEvent) {
                    if(getAttributesCombobox().getValueAsString() == null || getValueField().getValueAsString() == null ||
                            getValueField().getValueAsString().length() == 0){
                        SC.say(bundle.noAttributeOrValueSpecified());
                        return;
                    }
                    PatientAttributeValue patientAttributeValue = new PatientAttributeValue();
                    patientAttributeValue.setSuperKeyName(getAttributesCombobox().getValueAsString());
                    patientAttributeValue.put(AttributeValueColumns.ENTITY_ID.getColumnName(), patientId);
                    patientAttributeValue.put(AttributeValueColumns.ATTRIBUTE_ID.getColumnName(), getAttributesCombobox().getValueAsString());
                    patientAttributeValue.put(AttributeValueColumns.ATTRIBUTE_VALUE.getColumnName(), getValueField().getValueAsString());
                    ServiceStorage.getInstance().getPatientJmsServiceAsync().saveAttributeValue(patientAttributeValue, new AsyncCallback<Void>() {
                        public void onFailure(Throwable throwable) {
                            SC.say("Error:" + throwable);
                        }

                        public void onSuccess(Void aVoid) {
                            SC.say("Success");
                        }
                    });
                }
            });
        }
        return saveButton;
    }
}
