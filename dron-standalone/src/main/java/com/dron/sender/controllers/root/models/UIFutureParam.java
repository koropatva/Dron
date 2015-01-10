package com.dron.sender.controllers.root.models;

import com.dron.sender.sequence.models.FutureParam;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UIFutureParam {

	public static final String PROPERTY_KEY = "Key";

	public static final String PROPERTY_DEPENDENCE = "Dependence";

	public UIFutureParam() {
		this("", "");
	}

	public UIFutureParam(FutureParam futureParam) {
		keyProperty(futureParam.getKey());
		dependenceProperty(futureParam.getDependence());
	}

	public UIFutureParam(String key, String dependence) {
		keyProperty(key);
		dependenceProperty(dependence);
	}

	private StringProperty key;

	private StringProperty dependence;

	public StringProperty keyProperty() {
		return keyProperty(null);
	}

	public StringProperty keyProperty(String keyValue) {
		if (this.key == null) {
			this.key = new SimpleStringProperty(this, PROPERTY_KEY, keyValue);
		}
		return this.key;
	}

	public StringProperty dependenceProperty() {
		return dependenceProperty(null);
	}

	public StringProperty dependenceProperty(String dependenceValue) {
		if (this.dependence == null) {
			this.dependence = new SimpleStringProperty(this,
					PROPERTY_DEPENDENCE, dependenceValue);
		}
		return this.dependence;
	}

	public String getKey() {
		return key.get();
	}

	public void setKey(String key) {
		this.key.set(key);
	}

	public String getDependence() {
		return dependence.get();
	}

	public void setDependence(String dependence) {
		this.dependence.set(dependence);
	}

}
