package ua.com.alus.medhosp.frontend.client.utils;

import com.google.gwt.core.client.GWT;
import ua.com.alus.medhosp.frontend.client.resources.images.Icons;

/**
 * Constants
 * <p/>
 * Created by Usatov Alexey
 */
public class Constants {

    private Constants() {
        //empty
    }

    private static Constants instance = new Constants();

    public static Constants getInstance() {
        return instance;
    }

    private Icons icons;

    public Icons getIcons() {
        if (icons == null) {
            icons = (Icons) GWT.create(Icons.class);
        }
        return icons;
    }

}
