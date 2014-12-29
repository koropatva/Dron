package com.dron.sender.sequence.models;

public class Rule {

    private String key;
    
    private String value;
    
    private RuleCheckingEnum ruleCheckingEnum;
    
    private RuleSplitterEnum ruleSplitterEnum;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RuleCheckingEnum getRuleCheckingEnum() {
        return ruleCheckingEnum;
    }

    public void setRuleCheckingEnum(RuleCheckingEnum ruleCheckingEnum) {
        this.ruleCheckingEnum = ruleCheckingEnum;
    }

    public RuleSplitterEnum getRuleSplitterEnum() {
        return ruleSplitterEnum;
    }

    public void setRuleSplitterEnum(RuleSplitterEnum ruleSplitterEnum) {
        this.ruleSplitterEnum = ruleSplitterEnum;
    }
}