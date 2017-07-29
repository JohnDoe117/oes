package com.augmentum.oes;

import java.util.HashMap;
import java.util.Map;

import com.augmentum.oes.model.User;

public class AppContext {
    private static ThreadLocal<AppContext> appContextMap = new ThreadLocal<AppContext>();
    private Map<String, Object> objiects = new HashMap<String, Object>();
    private static String contextPath;

    public static String getContextPath() {
        return contextPath;
    }

    public static void setContextPath(String contextPath) {
        if (AppContext.contextPath == null) {
            AppContext.contextPath = contextPath;
        }
    }

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

    public User getUser() {
        return (User) objiects.get(Oes.APP_CONTEXT_USER);
    }

    public void setUser(User user) {
        objiects.put(Oes.APP_CONTEXT_USER, user);
    }

    public String getUserName() {
        User user = (User) objiects.get(Oes.APP_CONTEXT_USER);
        if (user != null) {
            return user.getUserName();
        }
        return "";
    }

    public int getUserId() {
        User user = (User) objiects.get(Oes.APP_CONTEXT_USER);
        if (user != null) {
            return user.getId();
        }
        return 0;
    }
}
