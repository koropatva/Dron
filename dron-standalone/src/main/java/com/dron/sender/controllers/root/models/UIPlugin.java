package com.dron.sender.controllers.root.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import com.dron.sender.models.UIHttpHeaders;
import com.dron.sender.models.UIHttpMethod;
import com.dron.sender.pattern.services.transformers.TransformerFactory;
import com.dron.sender.sequence.models.Plugin;

public class UIPlugin {

	public UIPlugin(final TextField tfUrl, final TextArea txaPostBody,
			final ChoiceBox<UIHttpMethod> cbMethods) {
		headersList.add(new UIHttpHeaders());

		spUrl = new SimpleStringProperty();
		tfUrl.textProperty().bindBidirectional(spUrl);

		spPostBody = new SimpleStringProperty();
		txaPostBody.textProperty().bindBidirectional(spPostBody);

		ssmMethod = cbMethods;
	}

	private StringProperty spUrl;

	private StringProperty spPostBody;

	private ChoiceBox<UIHttpMethod> ssmMethod;



	private final ObservableList<UIHttpHeaders> headersList = FXCollections
			.observableArrayList();

	public void fillPlugin(Plugin plugin) {
		spUrl.set(plugin.getUrl());
		spPostBody.set(plugin.getPostBody());
		ssmMethod.getItems().forEach(method -> {
			if (method.getMethod().equals(plugin.getHttpMethod().name())) {
				ssmMethod.getSelectionModel().select(method);
				return;
			}
		});

		headersList.clear();
		TransformerFactory.transformEntity(plugin.getHeaders(), headersList);
		headersList.add(new UIHttpHeaders());
	}

	public ObservableList<UIHttpHeaders> getHeadersList() {
		return headersList;
	}
}
