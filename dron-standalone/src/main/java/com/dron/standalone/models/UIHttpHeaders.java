package com.dron.standalone.models;

import org.apache.commons.lang3.StringUtils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UIHttpHeaders {

	public static final String PROPERTY_VALUE = "value";
	public static final String PROPERTY_HEADER = "header";

	public UIHttpHeaders(String header, String value) {
		this.header = headerProperty();
		this.header.set(header);
		this.value = valueProperty();
		this.value.set(value);
	}

	private StringProperty header;

	private StringProperty value;

	public boolean isEmpty() {
		return StringUtils.isBlank(header.get())
				&& StringUtils.isBlank(value.get());
	}

	public StringProperty headerProperty() {
		if (header == null) {
			header = new SimpleStringProperty(this, PROPERTY_HEADER);
		}
		return header;
	}

	public StringProperty valueProperty() {
		if (value == null) {
			value = new SimpleStringProperty(this, PROPERTY_VALUE);
		}
		return value;
	}

	public String getHeader() {
		return header.get();
	}

	public void setHeader(String header) {
		this.header.set(header);
	}

	public String getValue() {
		return value.get();
	}

	public void setValue(String value) {
		this.value.set(value);
	}
}
