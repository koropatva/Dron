package com.dron.sender.controllers.root.service;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.models.UIHttpMethod;
import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;

public class UISequenceService {

	public UISequenceService(final TextField tfUrl, final TextArea txaPostBody,
			final ChoiceBox<UIHttpMethod> cbMethods) {
		uiPlugin = new UIPlugin(tfUrl, txaPostBody, cbMethods);
	}

	private final List<Plugin> plugins = new ArrayList<Plugin>();

	private final UIPlugin uiPlugin;

	private Sequence sequence;

	public void fillSequence(Sequence sequence) {
		this.sequence = sequence;
		plugins.addAll(sequence.getPlugins());
		uiPlugin.fillPlugin(plugins.get(0));
	}

	public UIPlugin getUiPlugin() {
		return uiPlugin;
	}

	public Sequence getSequence() {
		return sequence;
	}

	public List<Plugin> getPlugins() {
		return plugins;
	}

}
