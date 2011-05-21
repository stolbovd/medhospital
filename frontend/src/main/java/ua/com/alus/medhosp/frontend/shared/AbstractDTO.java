package ua.com.alus.medhosp.frontend.shared;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 13.05.11
 * Time: 23:21
 */
public abstract class AbstractDTO extends HashMap<String, String> {
    public static final String KEY = "key";

    public abstract String[] getColumns();
}
