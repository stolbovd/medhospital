package ua.com.alus.medhosp.frontend.client.utils;

import com.google.gwt.core.client.GWT;
import ua.com.alus.medhosp.frontend.client.resources.locales.attrValues.AttributeValuesBundle;
import ua.com.alus.medhosp.frontend.client.resources.locales.attributes.AttributesBundle;
import ua.com.alus.medhosp.frontend.client.resources.locales.patients.PatientBundle;
import ua.com.alus.medhosp.frontend.client.resources.locales.tasks.TasksBundle;

/**
 * Created by Usatov Alexey
 * Date: 13.05.11
 * Time: 16:43
 */
public class Bundle {

    private Bundle() {
        //empty
    }

    private static Bundle instance = new Bundle();

    public static Bundle getInstance() {
        return instance;
    }

    private PatientBundle psBundle;

    public PatientBundle getPsBundle() {
        if (psBundle == null) {
            psBundle = (PatientBundle) GWT.create(PatientBundle.class);
        }
        return psBundle;
    }

    private TasksBundle tasksBundle;

    public TasksBundle getTasksBundle() {
        if (tasksBundle == null) {
            tasksBundle = (TasksBundle) GWT.create(TasksBundle.class);
        }
        return tasksBundle;
    }

    private AttributesBundle attributesBundle;

    public AttributesBundle getAttributesBundle() {
        if (attributesBundle == null) {
            attributesBundle = (AttributesBundle) GWT.create(AttributesBundle.class);
        }
        return attributesBundle;
    }

    private AttributeValuesBundle attributeValuesBundle;

    public AttributeValuesBundle getAttributeValuesBundle() {
        if (attributeValuesBundle == null) {
            attributeValuesBundle = (AttributeValuesBundle) GWT.create(AttributeValuesBundle.class);
        }
        return attributeValuesBundle;
    }
}
