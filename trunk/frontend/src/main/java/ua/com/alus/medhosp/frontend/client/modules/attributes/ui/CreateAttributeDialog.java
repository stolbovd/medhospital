package ua.com.alus.medhosp.frontend.client.modules.attributes.ui;

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
        final TextItem nameItem = new TextItem();
        nameItem.setTitle(bundle.name());

        IButton saveButton = new IButton(bundle.saveAttribute());
        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                if (nameItem.getValue() == null || nameItem.getValue().toString().trim().length() == 0) {
                    SC.say(bundle.nameCannotBeEmpty());
                    return;
                }
                AttributeDTO attributeDTO = new AttributeDTO();
                attributeDTO.put(BaseColumns.ENTITY_ID.getColumnName(), UUID.uuid());
                attributeDTO.put(AttributeColumns.NAME.getColumnName(), nameItem.getValueAsString());
                getController().saveAttribute(attributeDTO);
                destroy();
            }
        });

        form.setItems(nameItem);

        HLayout buttonLayout = new HLayout();
        buttonLayout.setAlign(Alignment.CENTER);
        buttonLayout.setMembers(saveButton);


        VLayout mainPanel = new VLayout(3);
        mainPanel.setMargin(5);
        mainPanel.setMembers(form, buttonLayout);

        addItem(mainPanel);
    }
}
