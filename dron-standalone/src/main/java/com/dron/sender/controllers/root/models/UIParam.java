package com.dron.sender.controllers.root.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.dron.sender.sequence.models.Param;

public class UIParam {

	public UIParam() {
		this("", "");
	}

	public UIParam(Param param) {
		keyProperty(param.getKey());
		valueProperty(param.getValue());
	}

	public UIParam(String key, String value) {
		keyProperty(key);
		valueProperty(value);
	}

	private StringProperty key;

	private StringProperty value;

	public StringProperty keyProperty() {
		return keyProperty(null);
	}

	public StringProperty keyProperty(String keyValue) {
		if (this.key == null) {
			this.key = new SimpleStringProperty(this, Param.PROPERTY_KEY,
					keyValue);
		}
		return this.key;
	}

	public StringProperty valueProperty() {
		return valueProperty(null);
	}

	public StringProperty valueProperty(String valueValue) {
		if (this.value == null) {
			this.value = new SimpleStringProperty(this, Param.PROPERTY_VALUE,
					valueValue);
		}
		return this.value;
	}

	public String getKey() {
		return key.get();
	}

	public void setKey(String value) {
		this.key.set(value);
	}

	public String getValue() {
		return value.get();
	}

	public void setValue(String value) {
		this.value.set(value);
	}

}
