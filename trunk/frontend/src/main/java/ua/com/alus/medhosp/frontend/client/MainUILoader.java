package ua.com.alus.medhosp.frontend.client;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import ua.com.alus.medhosp.frontend.client.modules.patients.ui.PatientsPanel;
import ua.com.alus.medhosp.frontend.client.modules.tasks.ui.TasksDialog;

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
        getMainPanel().draw();
    }

    public PatientsPanel getPatientsPanel() {
        if (patientsPanel == null) {
            patientsPanel = new PatientsPanel();
        }
        return patientsPanel;
    }

    private PatientsPanel patientsPanel;

    private HLayout topPanel;

    public HLayout getTopPanel() {
        if(topPanel == null){
            topPanel = new HLayout();

            IButton showTasksButton = new IButton();
            showTasksButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent clickEvent) {
                    getTasksDialog().show();
                }
            });
            showTasksButton.setWidth(70);
            showTasksButton.setHeight(20);
            showTasksButton.setTitle("Tasks");
            topPanel.setMembers(showTasksButton);
            topPanel.setMargin(20);
        }
        return topPanel;
    }

    private TasksDialog tasksDialog;

    public TasksDialog getTasksDialog() {
        if(tasksDialog == null){
            tasksDialog = new TasksDialog();
        }
        return tasksDialog;
    }

    private VLayout mainPanel;

    private VLayout getMainPanel(){
        if(mainPanel == null){
            mainPanel = new VLayout();
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
            //mainTabSet.setMargin(20);
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
