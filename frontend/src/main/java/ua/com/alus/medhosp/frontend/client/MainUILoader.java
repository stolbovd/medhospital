package ua.com.alus.medhosp.frontend.client;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import ua.com.alus.medhosp.frontend.client.modules.attributes.ui.AttributesPanel;
import ua.com.alus.medhosp.frontend.client.modules.patients.ui.PatientsPanel;
import ua.com.alus.medhosp.frontend.client.modules.tasks.ui.TasksDialog;
import ua.com.alus.medhosp.frontend.client.resources.images.Icons;
import ua.com.alus.medhosp.frontend.client.resources.locales.attributes.AttributesBundle;
import ua.com.alus.medhosp.frontend.client.resources.locales.patients.PatientBundle;
import ua.com.alus.medhosp.frontend.client.resources.locales.tasks.TasksBundle;
import ua.com.alus.medhosp.frontend.client.utils.Bundle;
import ua.com.alus.medhosp.frontend.client.utils.Constants;

/**
 * Created by Usatov Alexey
 * Date: 13.05.11
 * Time: 14:08
 */
public class MainUILoader {

    private static MainUILoader instance = new MainUILoader();
    private TasksBundle tasksBundle = Bundle.getInstance().getTasksBundle();
    private Icons icons = Constants.getInstance().getIcons();
    private PatientBundle patientBundle = Bundle.getInstance().getPsBundle();
    private AttributesBundle attributesBundle = Bundle.getInstance().getAttributesBundle();

    public static MainUILoader getInstance() {
        return instance;
    }

    public void drawMainUI() {
        getMainPanel().draw();
    }

    public PatientsPanel getPatientsPanel() {
        if (patientsPanel == null) {
            patientsPanel = new PatientsPanel();
        }
        return patientsPanel;
    }

    private PatientsPanel patientsPanel;

    private AttributesPanel attributesPanel;

    public AttributesPanel getAttributesPanel() {
        if (attributesPanel == null) {
            attributesPanel = new AttributesPanel();
        }
        return attributesPanel;
    }

    private HLayout topPanel;

    public HLayout getTopPanel() {
        if (topPanel == null) {
            topPanel = new HLayout();

            IButton showTasksButton = new IButton();
            showTasksButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent clickEvent) {
                    getTasksDialog().show();
                }
            });
            showTasksButton.setIcon(icons.tasks());
            showTasksButton.setWidth(50);
            showTasksButton.setHeight(50);
            showTasksButton.setIconSize(40);
            showTasksButton.setTooltip(tasksBundle.tasks());

            topPanel.setMembers(showTasksButton);
            topPanel.setMargin(20);
        }
        return topPanel;
    }

    private TasksDialog tasksDialog;

    public TasksDialog getTasksDialog() {
        if (tasksDialog == null) {
            tasksDialog = new TasksDialog();
        }
        return tasksDialog;
    }

    private VLayout mainPanel;

    private VLayout getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new VLayout();
            mainPanel.setMargin(10);
            mainPanel.setTop(40);
            mainPanel.setMembers(getTopPanel(), getMainTabset());
            mainPanel.setWidth100();
            mainPanel.setHeight100();
        }
        return mainPanel;
    }

    private TabSet mainTabSet;

    private TabSet getMainTabset() {
        if (mainTabSet == null) {
            mainTabSet = new TabSet();
            mainTabSet.setTabBarPosition(Side.TOP);
            mainTabSet.setTabBarAlign(Side.LEFT);
            mainTabSet.setWidth100();
            mainTabSet.setHeight100();
            mainTabSet.addTab(getPatientsTab());
            mainTabSet.addTab(getAttributesTab());
        }
        return mainTabSet;
    }

    private Tab patientsTab;

    public Tab getPatientsTab() {
        if (patientsTab == null) {
            patientsTab = new Tab(patientBundle.patients());
            patientsTab.setPane(getPatientsPanel());
        }
        return patientsTab;
    }

    private Tab attributesTab;

    public Tab getAttributesTab() {
        if (attributesTab == null) {
            attributesTab = new Tab(attributesBundle.attributes());
            attributesTab.setPane(getAttributesPanel());
        }
        return attributesTab;
    }
}
