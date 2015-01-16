package com.dron.sender.controllers.root.models;

import javafx.scene.control.ChoiceBox;

public class RootUIPlugin extends UIPlugin {

	private final ChoiceBox<String> cbMethods;

	public RootUIPlugin(final ChoiceBox<String> cbMethods) {
		this.cbMethods = cbMethods;
	}

	@Override
	public void setMethod(String method) {
		super.setMethod(method);
		cbMethods.getSelectionModel().select(method);
	}
}
