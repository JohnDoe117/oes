package com.augmentum.oes.exception;

import java.util.HashMap;
import java.util.Map;

public class ParameterException extends Exception {
    private static final long serialVersionUID = 6178176087883948563L;
    Map<String, String> errorFileMap = new HashMap<String, String>();

    public Map<String, String> getErrorFileMap() {
        return errorFileMap;
    }

    public void setErrorFileMap(Map<String, String> errorFileMap) {
        this.errorFileMap = errorFileMap;
    }

    public void adderrorFile(String file, String message) {
        errorFileMap.put(file, message);
    }

    public boolean isErrorField() {
        return !errorFileMap.isEmpty();
    }
}
