package com.dron.sender.controllers.base.helpers;

import javafx.util.StringConverter;

public class CellUtils {

	private final static StringConverter<?> defaultStringConverter = new StringConverter<Object>() {
		@Override
		public String toString(Object t) {
			return t == null ? null : t.toString();
		}

		@Override
		public Object fromString(String string) {
			return (Object) string;
		}
	};

	public static <T> StringConverter<T> defaultStringConverter() {
		return (StringConverter<T>) defaultStringConverter;
	}

}
