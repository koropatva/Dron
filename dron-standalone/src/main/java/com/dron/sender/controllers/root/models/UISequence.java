package com.dron.sender.controllers.root.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UISequence {

	public UISequence() {
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

}
