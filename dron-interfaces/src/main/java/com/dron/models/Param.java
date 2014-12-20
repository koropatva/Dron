package com.dron.models;

public class Param {

    public Param() {
    }
    
    public Param(final String key, final String value) {
        this(key, value, false);
    }

    public Param(final String key, final String value, final boolean array) {
        this.key = key;
        this.value = value;
        this.array = array;
    }

    private String key;

    private String value;

    private boolean array;

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
        // If we don't fill it before just create array with one element int the list
        if (array) {
            if (this.value == null) {
                this.value = "[" + value + "]";
            } else {
                this.value = this.value.substring(0, this.value.length() - 1) + ", " + value + "]";
            }
        } else {
            this.value = value;
        }
    }

    public boolean isArray() {
        return array;
    }

    public void setArray(boolean array) {
        this.array = array;
    }
}