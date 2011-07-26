package ua.com.alus.medhosp.frontend.client.modules.attributes.ui;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import ua.com.alus.medhosp.frontend.client.main.ui.MainPanel;
import ua.com.alus.medhosp.frontend.client.main.ui.ToolBarPanel;
import ua.com.alus.medhosp.frontend.client.main.ui.ToolbarButton;
import ua.com.alus.medhosp.frontend.client.resources.images.Icons;
import ua.com.alus.medhosp.frontend.client.resources.locales.attributes.AttributesBundle;
import ua.com.alus.medhosp.frontend.client.utils.Bundle;
import ua.com.alus.medhosp.frontend.client.utils.Constants;

/**
 * Panel of attributes
 * <p/>
 * Created by Usatov Alexey
 */
public class AttributesPanel extends HLayout {

    private AttributesTable attributesTable;
    private ToolBarPanel attributesToolbar;
    Icons icons = Constants.getInstance().getIcons();
    AttributesBundle bundle = Bundle.getInstance().getAttributesBundle();

    public AttributesPanel() {
        MainPanel mainPanel = new MainPanel();
        mainPanel.setMembers(getAttributesToolbar(), getAttributesTable());
        setMembers(mainPanel);
    }

    public AttributesTable getAttributesTable() {
        if (attributesTable == null) {
            attributesTable = new AttributesTable();
        }
        return attributesTable;
    }

    private ToolBarPanel getAttributesToolbar() {
        if (attributesToolbar == null) {
            attributesToolbar = new ToolBarPanel();

            final ToolbarButton refreshButton = new ToolbarButton(icons.refresh(),
                    bundle.refresh());


            refreshButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    getAttributesTable().getController().refreshTable();
                }
            });

            final ToolbarButton createButton = new ToolbarButton(icons.add(),
                    bundle.create());

            createButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent clickEvent) {
                    new CreateAttributeDialog(getAttributesTable().getController()).show();
                }
            });

            attributesToolbar.setMembers(createButton, refreshButton);
            attributesToolbar.setHeight(30);
        }
        return attributesToolbar;
    }

}
