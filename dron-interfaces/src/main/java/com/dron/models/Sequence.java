package com.dron.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Sequence {

    private List<Plugin> plugins = new LinkedList<Plugin>();

    private List<Param> params = new ArrayList<Param>();

    public String findParam(String key) {
        for (Param iterable : params) {
            if (iterable.getKey().equals(key)) {
                return iterable.getValue();
            }
        }
        return null;
    }

    public void updateParam(Param param) {
        for (Param iterable : params) {
            if (iterable.getKey().equals(param.getKey())) {
                iterable.setValue(param.getValue());
                break;
            }
        }
    }

    public List<Param> getParams() {
        return params;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }
}
