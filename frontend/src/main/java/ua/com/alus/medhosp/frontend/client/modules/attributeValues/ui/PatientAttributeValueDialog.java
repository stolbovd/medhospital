package ua.com.alus.medhosp.frontend.client.modules.attributeValues.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import ua.com.alus.medhosp.frontend.client.ServiceStorage;
import ua.com.alus.medhosp.frontend.client.main.ui.ToolBarPanel;
import ua.com.alus.medhosp.frontend.client.main.ui.ToolbarButton;
import ua.com.alus.medhosp.frontend.client.resources.images.Icons;
import ua.com.alus.medhosp.frontend.client.resources.locales.attrValues.AttributeValuesBundle;
import ua.com.alus.medhosp.frontend.client.utils.Bundle;
import ua.com.alus.medhosp.frontend.client.utils.Constants;
import ua.com.alus.medhosp.frontend.shared.AttributeDTO;
import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeValueColumns;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Dialog for view patient attribute values
 * <p/>
 * Created by Usatov Alexey
 */
public class PatientAttributeValueDialog extends Window {
    AttributeValuesBundle bundle = Bundle.getInstance().getAttributeValuesBundle();
    private VLayout mainPanel;
    private String entityId;
    private Icons icons = Constants.getInstance().getIcons();

    public PatientAttributeValueDialog(String entityId) {
        this.entityId = entityId;
        initDialog();
    }

    private void initDialog() {
        addItem(getMainPanel());
        setWidth(700);
        setHeight(700);
        centerInPage();
        retrieveData();
    }

    private VLayout addAttributeValuePanel;

    private VLayout getAddAttributeValuePanel() {
        if (addAttributeValuePanel == null) {
            addAttributeValuePanel = new VLayout();
            addAttributeValuePanel.setWidth100();
            addAttributeValuePanel.setHeight(100);

            DynamicForm addAttrValueForm = new DynamicForm();
            addAttrValueForm.setItems(getAttributesCombobox(), getValueField(), getSaveButton());
            //addAttrValueForm.setWidth(100);
            addAttrValueForm.setNumCols(2);

            addAttrValueForm.setAlign(Alignment.CENTER);
            //addAttrValueForm.setLayoutAlign(VerticalAlignment.CENTER);
            addAttributeValuePanel.setMembers(addAttrValueForm);
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
            attributesCombobox.setTitle(bundle.attribute());
            attributesCombobox.setType("comboBox");
            attributesCombobox.setWidth(150);
        }
        return attributesCombobox;
    }

    private VLayout getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new VLayout();
            mainPanel.setMembers(getToolbar(), getPatientAttributeValuesTable(), getAddAttributeValuePanel());
        }
        return mainPanel;
    }

    private ButtonItem saveButton;

    private ButtonItem getSaveButton() {
        if (saveButton == null) {
            saveButton = new ButtonItem();
            saveButton.setTitle(bundle.save());
            saveButton.setWidth(50);
            saveButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent clickEvent) {
                    if (getAttributesCombobox().getValueAsString() == null || getValueField().getValueAsString() == null ||
                            getValueField().getValueAsString().length() == 0) {
                        SC.say(bundle.noAttributeOrValueSpecified());
                        return;
                    }
                    PatientAttributeValue patientAttributeValue = new PatientAttributeValue();
                    patientAttributeValue.setSuperKeyName(getAttributesCombobox().getValueAsString());
                    patientAttributeValue.put(AttributeValueColumns.ENTITY_ID.getColumnName(), entityId);
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

    private PatientAttributeValuesTable patientAttributeValuesTable;

    public PatientAttributeValuesTable getPatientAttributeValuesTable() {
        if (patientAttributeValuesTable == null) {
            patientAttributeValuesTable = new PatientAttributeValuesTable(entityId);
        }
        return patientAttributeValuesTable;
    }

    private ToolBarPanel toolbar;

    private ToolBarPanel getToolbar() {
        if (toolbar == null) {
            toolbar = new ToolBarPanel();

            final ToolbarButton refreshButton = new ToolbarButton(icons.refresh(),
                    bundle.refresh());


            refreshButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
                public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                    getPatientAttributeValuesTable().getController().refreshTable();
                }
            });

            toolbar.setMembers(refreshButton);
            toolbar.setHeight(30);
        }
        return toolbar;
    }
}
