package com.dron.sender.controllers.root.models;

import com.dron.sender.controllers.root.RootController;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UISequence {

	private final StringProperty name = new SimpleStringProperty();

	private final ObservableList<String> order = FXCollections
			.observableArrayList();

	private final ObservableList<UIParam> uiParams = FXCollections
			.observableArrayList();

	private final ObservableList<UIPlugin> uiPlugins = FXCollections
			.observableArrayList();

	private int selectedUIPLugin;

	public void clear() {
		uiParams.clear();
		uiPlugins.clear();
		order.clear();
		name.set("");
		selectedUIPLugin = 0;
	}

	public void prepareEmptySequence() {
		selectedUIPLugin = 0;
		uiParams.add(new UIParam());
		uiPlugins.add(new UIPlugin());
		order.add(uiPlugins.get(0).getId().get());
	}

	public ObservableList<UIParam> getUIParams() {
		return uiParams;
	}

	public ObservableList<UIPlugin> getUiPlugins() {
		return uiPlugins;
	}

	public ObservableList<String> getOrder() {
		return order;
	}

	public StringProperty getName() {
		return name;
	}

	public UIPlugin getSelectedUIPLugin() {
		return uiPlugins.get(this.selectedUIPLugin);
	}

	public void selectedUIPLugin(int selectedUIPLugin,
			ControllerStrategyContext context, RootController controller) {
		if (selectedUIPLugin < 0) {
			selectedUIPLugin = 1;
		}
		this.selectedUIPLugin = selectedUIPLugin;
		context.execute(controller,
				ControllerActionStrategy.ROOT_FILL_ROOT_CONTROLS);
		controller.getAccPlugins().setExpandedPane(
				controller.getAccPlugins().getPanes()
						.get(this.selectedUIPLugin));
	}

}
