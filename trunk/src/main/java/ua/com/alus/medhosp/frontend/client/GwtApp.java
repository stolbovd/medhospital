package ua.com.alus.medhosp.frontend.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class GwtApp implements EntryPoint {


    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        MainUILoader.getInstance().drawMainUI();

    }
}
