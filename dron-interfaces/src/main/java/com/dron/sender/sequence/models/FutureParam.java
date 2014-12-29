package com.dron.sender.sequence.models;

import java.util.List;

public class FutureParam {

    public FutureParam() {
    }
    
    public FutureParam(final String key, final String dependence) {
        this.key = key;
        this.dependence = dependence;
    }

    private String key;

    private String dependence;

    private List<Rule> rules;
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDependence() {
        return dependence;
    }

    public void setDependence(String dependence) {
        this.dependence = dependence;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}