package com.augmentum.common;

import java.util.HashMap;
import java.util.Map;

public class ActionConfig {
    private String clsName;
    private String methodName;
    private String name;
    private String[] httpMethod;
    private Map<String, ResultConfig> results = new HashMap<String, ResultConfig>();

    public void addResults(String key, ResultConfig resultConfig) {
        results.put(key, resultConfig);
    }

    public ResultConfig getResult(String key) {
        return results.get(key);
    }

    public Map<String, ResultConfig> getResults() {
        return results;
    }

    public void setResults(Map<String, ResultConfig> results) {
        if (results == null) {
            results = new HashMap<String, ResultConfig>();
        }
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String[] httpMethod) {
        this.httpMethod = httpMethod;
    }

    public ActionConfig(String clsName, String methodName) {
        this.clsName = clsName;
        this.methodName = methodName;
    }

    public ActionConfig() {
    }

    public String getClsName() {
        return clsName;
    }

    public void setClsName(String clsName) {
        this.clsName = clsName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

}
