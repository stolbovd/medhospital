package ua.com.alus.medhosp.frontend.client.main.ui;

import com.smartgwt.client.widgets.IButton;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 13.05.11
 * Time: 14:33
 */
public class ToolbarButton extends IButton {
    public ToolbarButton(String icon, String tooltip) {
        super();
        setIcon(icon);
        setTooltip(tooltip);
        setHeight(25);
        setWidth(25);
    }
}
