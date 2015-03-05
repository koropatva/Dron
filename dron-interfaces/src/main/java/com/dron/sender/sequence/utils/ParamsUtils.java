package com.dron.sender.sequence.utils;

import java.util.List;
import java.util.UUID;

import com.dron.sender.sequence.models.Param;

public class ParamsUtils {

	private static final int DEFAULT_RANDOM_LENGHT = 7;
	private static final String PARAM_PREFIX = "{{";
	private static final String PARAM_SUFIX = "}}";
	private static final String RANDOM_VALUE_PREFIX = "{#";
	private static final String RANDOM_VALUE_SUFIX = "#}";

	public static String fillDataParams(String data, List<Param> params) {
		if (data == null)
			return null;

		String param = getFirstParamFromData(data);
		while (param != null) {
			data = data.replace(param, getValue(param, params));
			param = getFirstParamFromData(data);
		}
		data = fillRandomValue(data);
		return data;
	}

	public static String fillRandomValue(String data) {
		String randomValue = getFirstRandomValueFromData(data);
		while (randomValue != null) {
			data = data.replace(randomValue, getRandomValue());
			randomValue = getFirstRandomValueFromData(data);
		}
		return data;
	}

	public static String getRandomValue() {
		return getRandomValue(DEFAULT_RANDOM_LENGHT);
	}

	private static String getRandomValue(Integer lenght) {
		return UUID.randomUUID().toString().substring(0, lenght);
	}

	private static String getFirstParamFromData(String data) {
		int beginIndex = data.indexOf(PARAM_PREFIX);
		int endIndex = data.indexOf(PARAM_SUFIX) + PARAM_SUFIX.length();
		if (beginIndex >= 0 && endIndex >= 0) {
			return data.substring(beginIndex, endIndex);
		} else {
			return null;
		}
	}

	private static String getFirstRandomValueFromData(String data) {
		int beginIndex = data.indexOf(RANDOM_VALUE_PREFIX);
		int endIndex = data.indexOf(RANDOM_VALUE_SUFIX)
				+ RANDOM_VALUE_SUFIX.length();
		if (beginIndex >= 0 && endIndex >= 0) {
			return data.substring(beginIndex, endIndex);
		} else {
			return null;
		}
	}

	private static String getValue(String key, List<Param> params) {
		for (Param param : params) {
			if (param.getKey().equals(key)) {
				String newValue = fillRandomValue(param.getValue());
				if (!newValue.equals(param.getValue())) {
					param.setValue(newValue);
				}
				return newValue;
			}
		}
		return null;
	}
}