package ua.com.alus.medhosp.frontend.client.modules.patients.ui;

import ua.com.alus.medhosp.frontend.client.main.ui.MainPanel;
import ua.com.alus.medhosp.frontend.client.main.ui.ToolBarPanel;
import ua.com.alus.medhosp.frontend.client.resources.images.Icons;
import ua.com.alus.medhosp.frontend.client.resources.locales.patients.PatientConstants;
import ua.com.alus.medhosp.frontend.client.utils.ConstantsBundle;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import ua.com.alus.medhosp.frontend.client.main.ui.LeftToolPanel;
import ua.com.alus.medhosp.frontend.client.main.ui.ToolbarButton;


/**
 * Created by Usatov Alexey
 * Date: 02.05.11
 * Time: 13:05
 */
public class PatientsPanel extends HLayout {
    private ToolBarPanel patientsToolbar;
    PatientConstants constants = ConstantsBundle.getInstance().getPsConst();
    Icons icons = ConstantsBundle.getInstance().getIcons();

    public PatientsPanel() {


        MainPanel mainPanel = new MainPanel();
        mainPanel.setMembers(getPatientsToolbar(), getPatientsTable());

        LeftToolPanel leftToolPanel = new LeftToolPanel();
        leftToolPanel.setWidth(200);
        leftToolPanel.setHeight100();

        setMembers(leftToolPanel, mainPanel);

    }

    private ToolBarPanel getPatientsToolbar() {
        if (patientsToolbar == null) {
            patientsToolbar = new ToolBarPanel();

            final ToolbarButton createButton = new ToolbarButton(icons.add(),
                    constants.createPatient());

            final ToolbarButton getAllButton = new ToolbarButton(icons.refresh(),
                    constants.getAllPatients());


            final ToolbarButton deleteButton = new ToolbarButton(icons.remove(),
                    constants.deletePatients());
            deleteButton.setTooltip(constants.deletePatients());


            createButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    PatientDialog patientDialog = new PatientDialog(getPatientsTable().getController());
                    patientDialog.show();
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

