package com.augmentum.common;

import java.util.ArrayList;
import java.util.List;

public class ResultConfig {
    private String name;
    private String view;
    private Boolean redirect;

    private List<ViewParameterConfig> viewParameterConfigs = new ArrayList<ViewParameterConfig>();

    public List<ViewParameterConfig> getViewParameterConfigs() {
        return viewParameterConfigs;
    }

    public void addViewParameterConfigs(ViewParameterConfig viewParameterConfig) {
        this.viewParameterConfigs.add(viewParameterConfig);
    }

    public void setViewParameterConfigs(List<ViewParameterConfig> viewParameterConfigs) {
        if (viewParameterConfigs == null) {
            viewParameterConfigs = new ArrayList<ViewParameterConfig>();
        }
        this.viewParameterConfigs = viewParameterConfigs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Boolean getRedirect() {
        return redirect;
    }

    public void setRedirect(Boolean redirect) {
        this.redirect = redirect;
    }

}
