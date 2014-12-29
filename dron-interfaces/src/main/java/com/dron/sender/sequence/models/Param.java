package com.dron.sender.sequence.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import com.dron.sender.pattern.interfaces.IBaseObserver;

public class Param implements IBaseObserver {

	private static final String PROPERTY_ARRAY = "Array";

	private static final String PROPERTY_VALUE = "Value";

	private static final String PROPERTY_KEY = "Key";

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

	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

	private String key;

	private String value;

	private boolean array;

	public void addChangeListener(PropertyChangeListener newListener) {
		listeners.add(newListener);
	}

	private void notifyListeners(Object object, String property,
			Object oldValue, Object newValue) {
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(object, property,
					oldValue, newValue));
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		notifyListeners(this, PROPERTY_KEY, this.key, this.key = key);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		// If we don't fill it before just create array with one element int the
		// list
		String newValue;
		if (array) {
			if (this.value == null) {
				newValue = "[" + value + "]";
			} else {
				newValue = this.value.substring(0, this.value.length() - 1)
						+ ", " + value + "]";
			}
		} else {
			newValue = value;
		}
		notifyListeners(this, PROPERTY_VALUE, this.value, this.value = newValue);
	}

	public boolean isArray() {
		return array;
	}

	public void setArray(boolean array) {
		notifyListeners(this, PROPERTY_ARRAY, this.array, this.array = array);
	}
}