package com.dron.sender.sequence.utils;

import java.util.List;

import com.dron.sender.sequence.models.Param;

public class ParamsUtils {

    public static String fillDataParams(String data, List<Param> params) {
        if (data == null) return null;

        String param = getFirstParamFromData(data);
        while (param != null) {
            data = data.replace(param, getValue(param, params));
            param = getFirstParamFromData(data);
        };
        return data;
    }

    private static String getFirstParamFromData(String data) {
        int beginIndex = data.indexOf("{{");
        int endIndex = data.indexOf("}}") + "}}".length();
        if (beginIndex >= 0 && endIndex >= 0) {
            return data.substring(beginIndex, endIndex);
        } else {
            return null;
        }
    }

    private static String getValue(String key, List<Param> params) {
        for (Param param : params) {
            if (param.getKey().equals(key)) {
                return param.getValue();
            }
        }
        return null;
    }
}