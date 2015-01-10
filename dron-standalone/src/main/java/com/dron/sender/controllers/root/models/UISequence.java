package com.dron.sender.controllers.root.models;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;

public class UISequence {

	private final Map<Plugin, ObservableList<UIFutureParam>> mapFutureParams = new LinkedHashMap<>();

	private final ObservableList<UIParam> uiParams = FXCollections
			.observableArrayList();

	private final UIPlugin uiPlugin;

	public UISequence(final TextField tfUrl, final TextArea txaPostBody,
			final ChoiceBox<UIHttpMethod> cbMethods) {
		uiPlugin = new UIPlugin(tfUrl, txaPostBody, cbMethods);
	}

	public ObservableList<UIParam> getUIParams() {
		return uiParams;
	}

	public Map<Plugin, ObservableList<UIFutureParam>> getMapFutureParams() {
		return mapFutureParams;
	}

	public UIPlugin getUiPlugin() {
		return uiPlugin;
	}

	//TODO REMOVE 
	public Sequence getSequence() {
		return null;
	}
}
