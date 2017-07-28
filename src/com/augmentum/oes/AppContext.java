package com.augmentum.oes;

import java.util.HashMap;
import java.util.Map;

public class AppContext {
    private static ThreadLocal<AppContext> appContextMap = new ThreadLocal<AppContext>();
    private Map<String, Object> objiects = new HashMap<String, Object>();

    private AppContext() {
    }

    public static AppContext getAppContext() {
        AppContext appContext = appContextMap.get();
        if (appContext == null) {
            appContext = new AppContext();
            appContextMap.set(appContext);
        }
        return appContextMap.get();
    }

    public Map<String, Object> getObjiects() {
        return objiects;
    }

    public void addObjiect(String key, Object object) {
        this.objiects.put(key, object);
    }

    public Object getObjiect(String key) {
        return objiects.get(key);
    }

    public void clear() {
        objiects.clear();
    }

    public void setObjiects(Map<String, Object> objiects) {
        if (objiects == null) {
            objiects = new HashMap<String, Object>();
        }
        this.objiects = objiects;
    }

    public void removeObject(String string) {
        this.removeObject(string);
    }

}
