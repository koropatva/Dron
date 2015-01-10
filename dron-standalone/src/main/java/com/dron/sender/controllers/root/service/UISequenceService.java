package com.dron.sender.controllers.root.service;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import com.dron.sender.controllers.root.models.UIFutureParam;
import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.models.UIHttpMethod;
import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;

public class UISequenceService {

	public UISequenceService(final TextField tfUrl, final TextArea txaPostBody,
			final ChoiceBox<UIHttpMethod> cbMethods) {
		uiPlugin = new UIPlugin(tfUrl, txaPostBody, cbMethods);
	}

	private final Map<Plugin, ObservableList<UIFutureParam>> mapFutureParams = new LinkedHashMap<>();

	private final UIPlugin uiPlugin;

	private Sequence sequence;

	public void fillSequence(Sequence sequence) {
		this.sequence = sequence;
		mapFutureParams.clear();
		sequence.getPlugins().forEach(
				plugin -> {
					ObservableList<UIFutureParam> futureParams = FXCollections
							.observableArrayList();
					plugin.getFutureParams().forEach(
							futureParam -> futureParams.add(new UIFutureParam(
									futureParam)));
					futureParams.add(new UIFutureParam());
					mapFutureParams.put(plugin, futureParams);
				});
		uiPlugin.fillPlugin(sequence.getPlugins().get(0));
	}

	public UIPlugin getUiPlugin() {
		return uiPlugin;
	}

	public Sequence getSequence() {
		return sequence;
	}

	public Map<Plugin, ObservableList<UIFutureParam>> getMapFutureParams() {
		return mapFutureParams;
	}
}
