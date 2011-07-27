package ua.com.alus.medhosp.frontend.client.modules.attributes.ui;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import ua.com.alus.medhosp.frontend.client.resources.locales.attributes.AttributesBundle;
import ua.com.alus.medhosp.frontend.client.utils.Bundle;
import ua.com.alus.medhosp.frontend.shared.AttributeDTO;
import ua.com.alus.medhosp.prototype.cassandra.dto.AttributeColumns;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;
import ua.com.alus.medhosp.prototype.util.UUID;


/**
 * Created by Usatov Alexey
 * Date: 03.05.11
 * Time: 11:52
 */
public class CreateAttributeDialog extends Window {
    AttributesBundle bundle = Bundle.getInstance().getAttributesBundle();

    private AttributesController controller;

    private AttributesController getController() {
        return controller;
    }

    private TextItem shortCodeItem;
    private TextItem nameItem;

    public CreateAttributeDialog(AttributesController controller) {
        super();
        this.controller = controller;

        setTitle(bundle.create());
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

        shortCodeItem = new TextItem();
        shortCodeItem.setTitle(bundle.shortCode());
        shortCodeItem.setRequired(true);


        nameItem = new TextItem();
        nameItem.setTitle(bundle.name());
        nameItem.setRequired(true);

        IButton saveButton = new IButton(bundle.saveAttribute());
        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                if (validateForm()) {
                    AttributeDTO attributeDTO = new AttributeDTO();
                    attributeDTO.put(BaseColumns.ENTITY_ID.getColumnName(), shortCodeItem.getValueAsString());
                    attributeDTO.put(AttributeColumns.NAME.getColumnName(), nameItem.getValueAsString());
                    getController().saveAttribute(attributeDTO);
                    destroy();
                }
            }
        });

        form.setItems(shortCodeItem, nameItem);

        HLayout buttonLayout = new HLayout();
        buttonLayout.setAlign(Alignment.CENTER);
        buttonLayout.setMembers(saveButton);


        VLayout mainPanel = new VLayout(3);
        mainPanel.setMargin(5);
        mainPanel.setMembers(form, buttonLayout);

        addItem(mainPanel);
    }


    private boolean validateForm() {
        if (nameItem.getValue() == null || nameItem.getValue().toString().trim().length() == 0) {
            SC.say(bundle.nameCannotBeEmpty());
            return false;
        }
        if (shortCodeItem.getValue() == null || shortCodeItem.getValue().toString().trim().length() == 0) {
            SC.say(bundle.shortCodeCannotBeEmpty());
            return false;
        }
        return true;
    }
}
