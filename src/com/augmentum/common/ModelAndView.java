package com.augmentum.common;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private Map<String, Object> sessions = new HashMap<String, Object>();
    private Map<String, Object> requests = new HashMap<String, Object>();
    private String view;
    private Boolean isRedirect = false;

    public void addObject(String key, Object value) {
        requests.put(key, value);
    }

    public Map<String, Object> getRequests() {
        return requests;
    }

    public String getView() {
        return view;
    }

    public Map<String, Object> getSessions() {
        return sessions;
    }

    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Boolean getIsRedirect() {
        return isRedirect;
    }

    public void setIsRedirect(Boolean isRedirect) {
        this.isRedirect = isRedirect;
    }

    public void addSessionAttribute(String key, Object object) {
        sessions.put(key, object);
    }
}
