package ua.com.alus.medhosp.frontend.client.utils;

import com.google.gwt.core.client.GWT;
import ua.com.alus.medhosp.frontend.client.resources.images.Icons;
import ua.com.alus.medhosp.frontend.client.resources.locales.patients.PatientConstants;

/**
 * Created by Usatov Alexey
 * Date: 13.05.11
 * Time: 16:43
 */
public class ConstantsBundle {

    private static ConstantsBundle instance = new ConstantsBundle();

    public static ConstantsBundle getInstance() {
        return instance;
    }

    private PatientConstants psConst;

    public PatientConstants getPsConst() {
        if (psConst == null) {
            psConst = (PatientConstants) GWT.create(PatientConstants.class);
        }
        return psConst;
    }

    private Icons icons;

    public Icons getIcons() {
        if (icons == null) {
            icons = (Icons) GWT.create(Icons.class);
        }
        return icons;
    }
}
