package ua.com.alus.medhosp.frontend.client.modules.patients.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import ua.com.alus.medhosp.frontend.client.ServiceStorage;
import ua.com.alus.medhosp.frontend.client.main.ui.MainPanel;
import ua.com.alus.medhosp.frontend.client.main.ui.ToolBarPanel;
import ua.com.alus.medhosp.frontend.client.main.ui.ToolbarButton;
import ua.com.alus.medhosp.frontend.client.resources.images.Icons;
import ua.com.alus.medhosp.frontend.client.resources.locales.patients.PatientBundle;
import ua.com.alus.medhosp.frontend.client.utils.Bundle;
import ua.com.alus.medhosp.frontend.client.utils.Constants;
import ua.com.alus.medhosp.frontend.shared.AttributeDTO;
import ua.com.alus.medhosp.frontend.shared.PatientAttributeValue;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;
import ua.com.alus.medhosp.prototype.util.UUID;

import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by Usatov Alexey
 * Date: 02.05.11
 * Time: 13:05
 */
public class PatientsPanel extends HLayout {
    private ToolBarPanel patientsToolbar;
    PatientBundle bundle = Bundle.getInstance().getPsBundle();
    Icons icons = Constants.getInstance().getIcons();

    public PatientsPanel() {


        MainPanel mainPanel = new MainPanel();
        mainPanel.setMembers(getPatientsToolbar(), getPatientsTable());

        setMembers(mainPanel);
        retrieveData();
    }

    private ToolBarPanel getPatientsToolbar() {
        if (patientsToolbar == null) {
            patientsToolbar = new ToolBarPanel();

            final ToolbarButton createButton = new ToolbarButton(icons.add(),
                    bundle.createPatient());

            final ToolbarButton getAllButton = new ToolbarButton(icons.refresh(),
                    bundle.getAllPatients());


            final ToolbarButton deleteButton = new ToolbarButton(icons.remove(),
                    bundle.deletePatients());
            deleteButton.setTooltip(bundle.deletePatients());


            createButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    SC.confirm(bundle.createPatient(), bundle.confirmContinute(), new BooleanCallback() {
                        public void execute(Boolean aBoolean) {
                            if (aBoolean) {
                                PatientDTO patientDTO = new PatientDTO();
                                patientDTO.put(BaseColumns.ENTITY_ID.getColumnName(), UUID.uuid());
                                getPatientsTable().getController().createPatient(patientDTO);
                            }
                        }
                    });
                }
            });

            getAllButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    getPatientsTable().getController().refreshTable();
                }
            });

            deleteButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    getPatientsTable().getController().removeSelected();
                }
            });

            DynamicForm searchPanel = new DynamicForm();
            searchPanel.setFields(getAttributesCombobox(), getSearchField());
            searchPanel.setWidth(400);
            searchPanel.setNumCols(5);

            patientsToolbar.setMembers(createButton, deleteButton, getAllButton, searchPanel, getSearchButton());
            patientsToolbar.setHeight(30);
            patientsToolbar.setWidth(400);
        }
        return patientsToolbar;
    }

    private ComboBoxItem attributesCombobox;

    private ComboBoxItem getAttributesCombobox() {
        if (attributesCombobox == null) {
            attributesCombobox = new ComboBoxItem();
            attributesCombobox.setTitle(bundle.searchByAttribute());
            //attributesCombobox.setHint("<nobr>" + bundle.searchByAttribute() + "</nobr>");
            attributesCombobox.setType("comboBox");
            attributesCombobox.setWidth(150);
        }
        return attributesCombobox;
    }

    private TextItem searchField;

    private TextItem getSearchField() {
        if (searchField == null) {
            searchField = new TextItem();
            searchField.setTitle("");
        }
        return searchField;
    }


    private IButton searchButton;

    private IButton getSearchButton() {
        if (searchButton == null) {
            searchButton = new IButton();
            searchButton.setTitle(bundle.search());
            searchButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    String search;
                    if ((search = searchField.getValueAsString()) != null && getAttributesCombobox().getValueAsString() != null) {
                        searchByAttribute(getAttributesCombobox().getValueAsString(), search);
                    }
                }
            });
        }
        return searchButton;
    }

    private void searchByAttribute(String attributeId, String value) {
        ServiceStorage.getInstance().getPatientServiceAsync().getPatientsByAttributeValue(attributeId, value, new AsyncCallback<List<PatientAttributeValue>>() {
            public void onFailure(Throwable throwable) {
                SC.say("Error:" + throwable);
            }

            public void onSuccess(List<PatientAttributeValue> attributeDTOs) {
            }
        });
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

    private PatientsTable patientsTable;

    public PatientsTable getPatientsTable() {
        if (patientsTable == null) {
            patientsTable = new PatientsTable();
        }
        return patientsTable;
    }

}

