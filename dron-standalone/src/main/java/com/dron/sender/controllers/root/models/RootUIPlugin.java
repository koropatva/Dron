package com.dron.sender.controllers.root.models;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RootUIPlugin extends UIPlugin {

	private final ChoiceBox<String> cbMethods;

	public RootUIPlugin(final TextField tfUrl, final TextArea txaPostBody,
			final ChoiceBox<String> cbMethods) {

		tfUrl.textProperty().bindBidirectional(url);

		txaPostBody.textProperty().bindBidirectional(postBody);

		this.cbMethods = cbMethods;
		cbMethods
				.getSelectionModel()
				.selectedItemProperty()
				.addListener(
						(ObservableValue<? extends String> observable,
								String oldValue, String newValue) -> {
							if (newValue != null) {
								super.setMethod(newValue);
							}
						});
	}

	@Override
	public void setMethod(String method) {
		super.setMethod(method);
		cbMethods.getSelectionModel().select(method);
	}
}
