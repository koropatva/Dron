package com.dron.sender.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UIHttpHeaders {

	public static final String PROPERTY_VALUE = "Value";
	public static final String PROPERTY_HEADER = "Header";

	public UIHttpHeaders() {
		this("", "");
	}

	public UIHttpHeaders(String header, String value) {
		headerProperty(header);
		valueProperty(value);
	}

	private StringProperty header;

	private StringProperty value;

	public StringProperty headerProperty() {
		return headerProperty(null);
	}

	public StringProperty headerProperty(String initialValue) {
		if (this.header == null) {
			this.header = new SimpleStringProperty(this, PROPERTY_HEADER,
					initialValue);
		}
		return this.header;
	}

	public StringProperty valueProperty() {
		return valueProperty(null);
	}

	public StringProperty valueProperty(String initialValue) {
		if (this.value == null) {
			this.value = new SimpleStringProperty(this, PROPERTY_VALUE,
					initialValue);
		}
		return this.value;
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
