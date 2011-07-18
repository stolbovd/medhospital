package ua.com.alus.medhosp.frontend.client.modules.patients.ui;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import ua.com.alus.medhosp.frontend.client.resources.locales.patients.PatientConstants;
import ua.com.alus.medhosp.frontend.client.utils.ConstantsBundle;
import ua.com.alus.medhosp.frontend.shared.PatientDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;
import ua.com.alus.medhosp.prototype.util.UUID;


/**
 * Created by Usatov Alexey
 * Date: 03.05.11
 * Time: 11:52
 */
public class PatientDialog extends Window {
    PatientConstants constants = ConstantsBundle.getInstance().getPsConst();

    private IController controller;

    private IController getController() {
        return controller;
    }

    public PatientDialog(IController controller) {
        super();
        this.controller = controller;

        setTitle(constants.createPatient());
        setWidth(400);
        setHeight(200);
        setIsModal(true);
        setShowMinimizeButton(false);
        setShowModalMask(true);
        centerInPage();

        DynamicForm form = new DynamicForm();
        form.setHeight100();
        form.setWidth100();
        form.setLayoutAlign(VerticalAlignment.BOTTOM);
        final TextItem nameItem = new TextItem();
        nameItem.setTitle(constants.name());

        final TextItem lastNameItem = new TextItem();
        lastNameItem.setTitle(constants.lastName());

        IButton saveButton = new IButton(constants.savePatient());
        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                if (lastNameItem.getValue() == null || lastNameItem.getValue().toString().trim().length() == 0) {
                    SC.say(constants.nameCannotBeEmpty());
                    return;
                }
                PatientDTO patientDTO = new PatientDTO();
                patientDTO.put(BaseColumns.ENTITY_ID.getColumnName(), UUID.uuid());
                getController().createPatient(patientDTO);
                destroy();
            }
        });

        form.setItems(lastNameItem, nameItem);

        HLayout buttonLayout = new HLayout();
        buttonLayout.setAlign(Alignment.CENTER);
        buttonLayout.setMembers(saveButton);


        VLayout mainPanel = new VLayout(3);
        mainPanel.setMargin(5);
        mainPanel.setMembers(form, buttonLayout);

        addItem(mainPanel);
    }
}
