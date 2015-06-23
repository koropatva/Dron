package com.dron.sender.controllers.root.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HistoryUiPlugin {
	private UIPlugin uiPlugin;

	private final ObservableList<UIParam> uiParams = FXCollections
			.observableArrayList();

	public HistoryUiPlugin(UIPlugin uiPlugin, ObservableList<UIParam> uiParams) {
		this.uiPlugin = uiPlugin.clone();
		this.uiParams.clear();
		this.uiParams.addAll(uiParams);
	}

	public ObservableList<UIParam> getUiParams() {
		return uiParams;
	}

	public UIPlugin getUiPlugin() {
		return uiPlugin;
	}

	@Override
	public String toString() {
		return uiPlugin.getName().get() + " " + uiPlugin.isSuccess();
	}

}
