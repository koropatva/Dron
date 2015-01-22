package com.dron.sender.controllers.root.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UISequence {

	private final StringProperty name = new SimpleStringProperty();

	private final ObservableList<UIParam> uiParams = FXCollections
			.observableArrayList();

	private final ObservableList<UIPlugin> uiPlugins = FXCollections
			.observableArrayList();

	public void clear() {
		uiParams.clear();
		uiPlugins.clear();
		name.set("");
	}

	public void prepareEmptySequence() {
		uiParams.add(new UIParam());
		uiPlugins.add(new UIPlugin());
	}

	public ObservableList<UIParam> getUIParams() {
		return uiParams;
	}

	public ObservableList<UIPlugin> getUiPlugins() {
		return uiPlugins;
	}

	public StringProperty getName() {
		return name;
	}

}
