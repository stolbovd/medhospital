package ua.com.alus.medhosp.frontend.client.modules.patients.ui;

import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import ua.com.alus.medhosp.frontend.client.main.ui.MainPanel;
import ua.com.alus.medhosp.frontend.client.main.ui.ToolBarPanel;
import ua.com.alus.medhosp.frontend.client.main.ui.ToolbarButton;
import ua.com.alus.medhosp.frontend.client.resources.images.Icons;
import ua.com.alus.medhosp.frontend.client.resources.locales.patients.PatientBundle;
import ua.com.alus.medhosp.frontend.client.utils.Bundle;
import ua.com.alus.medhosp.frontend.client.utils.Constants;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;
import ua.com.alus.medhosp.prototype.util.UUID;


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

            patientsToolbar.setMembers(createButton, deleteButton, getAllButton);
            patientsToolbar.setHeight(30);
        }
        return patientsToolbar;
    }


    private PatientsTable patientsTable;

    public PatientsTable getPatientsTable() {
        if (patientsTable == null) {
            patientsTable = new PatientsTable();
        }
        return patientsTable;
    }

}

