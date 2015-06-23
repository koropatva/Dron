package com.dron.sender.controllers.root.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HistoryUiPlugin {
	private UIPlugin uiPlugin;

	private final ObservableList<UIParam> uiParams = FXCollections
			.observableArrayList();

	private final ObservableList<UIHttpHeaders> headersList = FXCollections
			.observableArrayList();

	public ObservableList<UIParam> getUiParams() {
		return uiParams;
	}

	public UIPlugin getUiPlugin() {
		return uiPlugin;
	}

	public ObservableList<UIHttpHeaders> getHeadersList() {
		return headersList;
	}

	@Override
	public String toString() {
		return uiPlugin.getName().get();
	}

}
