package com.dron.sender.controllers.root.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class UISequence {

	private final StringProperty name = new SimpleStringProperty();

	public UISequence(TextField tfName) {
		tfName.textProperty().bindBidirectional(name);
		uiParams.add(new UIParam());
		uiPlugins.add(new UIPlugin());
	}

	private final ObservableList<UIParam> uiParams = FXCollections
			.observableArrayList();

	private final ObservableList<UIPlugin> uiPlugins = FXCollections
			.observableArrayList();

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
