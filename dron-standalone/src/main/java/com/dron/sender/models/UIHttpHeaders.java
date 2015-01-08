package com.dron.sender.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UIHttpHeaders {

	public static final String PROPERTY_VALUE = "Value";
	public static final String PROPERTY_HEADER = "Header";

	public UIHttpHeaders(String header, String value) {
		this.header = headerProperty();
		this.header.set(header);
		this.value = valueProperty();
		this.value.set(value);
	}

	private StringProperty header;

	private StringProperty value;

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
