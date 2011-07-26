package ua.com.alus.medhosp.frontend.client.main.ui;

import com.smartgwt.client.widgets.IButton;

/**
 * Created by Usatov Alexey
 * Date: 13.05.11
 * Time: 14:33
 */
public class ToolbarButton extends IButton {
    public ToolbarButton(String icon, String tooltip) {
        super();
        setIcon(icon);
        setIconSize(24);
        setTooltip(tooltip);
        setHeight(30);
        setWidth(30);
    }
}
