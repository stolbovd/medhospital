package ua.com.alus.medhosp.frontend.server.services.spring.dao;

import java.util.HashMap;

/**
 * Just a class for incapsulate all settings of search
 */
public class CassandraSearch {
    private String keyStart = "";
    private String keyEnd = "";
    private int count = 100;
    private HashMap<String, HashMap<String, String>> superNames2Values = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, String> simpleNames2Values = new HashMap<String, String>();

    private boolean returnOnlyKeys = false;

    public String getKeyStart() {
        return keyStart;
    }

    public void setKeyStart(String keyStart) {
        this.keyStart = keyStart;
    }

    public String getKeyEnd() {
        return keyEnd;
    }

    public void setKeyEnd(String keyEnd) {
        this.keyEnd = keyEnd;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public HashMap<String, HashMap<String, String>> getSuperNames2Values() {
        return superNames2Values;
    }

    public void setNames2Values(HashMap<String, HashMap<String, String>> superNames2Values) {
        this.superNames2Values = superNames2Values;
    }

    public boolean isReturnOnlyKeys() {
        return returnOnlyKeys;
    }

    public void setReturnOnlyKeys(boolean returnOnlyKeys) {
        this.returnOnlyKeys = returnOnlyKeys;
    }

    public HashMap<String, String> getSimpleNames2Values() {
        return simpleNames2Values;
    }

    public void setSimpleNames2Values(HashMap<String, String> simpleNames2Values) {
        this.simpleNames2Values = simpleNames2Values;
    }

    private String[] superColumnsNames;

    public String[] getSuperColumnsName() {
        if (superColumnsNames == null) {
            if (getSuperNames2Values().size() != 0) {
                superColumnsNames = getSuperNames2Values().keySet().toArray(new String[]{""});
            } else {
                superColumnsNames = new String[]{};
            }
        }
        return superColumnsNames;
    }
}
