package ua.com.alus.medhosp.frontend.client;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import ua.com.alus.medhosp.frontend.client.modules.patients.ui.PatientsPanel;

/**
 * Created by Usatov Alexey
 * Date: 13.05.11
 * Time: 14:08
 */
public class MainUILoader {

    private static MainUILoader instance = new MainUILoader();

    public static MainUILoader getInstance() {
        return instance;
    }

    public void drawMainUI() {
        getMainTabset().draw();
    }

    public PatientsPanel getPatientsPanel() {
        if (patientsPanel == null) {
            patientsPanel = new PatientsPanel();
        }
        return patientsPanel;
    }

    private PatientsPanel patientsPanel;

    private TabSet mainTabSet;

    private TabSet getMainTabset() {
        if (mainTabSet == null) {
            mainTabSet = new TabSet();
            mainTabSet.setMargin(20);
            mainTabSet.setTabBarPosition(Side.TOP);
            mainTabSet.setTabBarAlign(Side.LEFT);
            mainTabSet.setWidth100();
            mainTabSet.setHeight100();
            mainTabSet.addTab(getPatientsTab());
        }
        return mainTabSet;
    }

    private Tab patientsTab;

    public Tab getPatientsTab() {
        if (patientsTab == null) {
            patientsTab = new Tab("Patients");
            patientsTab.setPane(getPatientsPanel());
        }
        return patientsTab;
    }
}
