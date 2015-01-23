package com.dron.sender.pattern.services.strategies;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.FutureParamTableView;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.models.UIFutureParam;
import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;

@Component
public class FillUIPluginAccordionStrategy extends ModelRootController
		implements IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.FILL_UI_PLUGIN_ACCORDION;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		final RootController controller = (RootController) iBaseController;
		setUp(controller);

		List<TitledPane> titledPanes = new ArrayList<TitledPane>();

		if (uiSequence.getUiPlugins().isEmpty()) {
			uiSequence.getUiPlugins().add(new UIPlugin());
		}

		uiSequence.getUiPlugins().forEach(uiPlugin -> {
			titledPanes.add(createPluginTitlePane(uiPlugin, controller));
		});

		accPlugins.getPanes().clear();
		accPlugins.getPanes().addAll(titledPanes);
		accPlugins.setExpandedPane(accPlugins.getPanes().get(
				RootController.DEFAULT_SELECTED_UI_PLUGIN));

		accPlugins.expandedPaneProperty()
				.addListener(
						(ObservableValue<? extends TitledPane> observable,
								TitledPane oldValue, TitledPane newValue) -> {
							if (oldValue != null) {
								RootConfig.setDisableRootProperty(true);
							}
							if (newValue != null) {
								int index = accPlugins
										.getChildrenUnmodifiable().indexOf(
												newValue);

								uiSequence.selectedUIPLugin(index, context,
										controller);
								RootConfig.setDisableRootProperty(false);
							}
						});

	}

	private TitledPane createPluginTitlePane(UIPlugin uiPlugin,
			RootController controller) {

		AnchorPane anchorPane = createAnchorPane(uiPlugin, controller);

		TitledPane pluginTitle = new TitledPane(uiPlugin.getName().get(),
				anchorPane);
		pluginTitle.textProperty().bindBidirectional(uiPlugin.getName());
		return pluginTitle;
	}

	private AnchorPane createAnchorPane(UIPlugin uiPlugin,
			RootController controller) {
		TableView<UIFutureParam> tblFutureParams = new TableView<>();
		tblFutureParams.setPrefWidth(DEFAULT_ACCORDION_WIDTH);
		tblFutureParams = new FutureParamTableView().initialize(
				uiPlugin.getFutureParams(), tblFutureParams);

		TextField tfPluginName = new TextField();
		tfPluginName.setPrefHeight(16.0);
		tfPluginName.setPrefWidth(DEFAULT_ACCORDION_WIDTH);
		tfPluginName.textProperty().bindBidirectional(uiPlugin.getName());

		Button btnRemove = new Button("Remove");
		btnRemove
				.setOnAction(listener -> {
					int expantedIndex = getExpantedUIPluginIndex();
					uiSequence.getUiPlugins().remove(
							uiSequence.getUiPlugins().get(expantedIndex));

					context.execute(controller,
							ControllerActionStrategy.FILL_UI_PLUGIN_ACCORDION);
					uiSequence.selectedUIPLugin(expantedIndex - 1, context,
							controller);
				});

		AnchorPane anchorPane = new AnchorPane();
		anchorPane.getChildren().addAll(
				new VBox(tfPluginName, tblFutureParams, btnRemove));
		anchorPane.setPrefWidth(DEFAULT_ACCORDION_WIDTH);
		return anchorPane;
	}

	private int getExpantedUIPluginIndex() {
		return accPlugins.getChildrenUnmodifiable().indexOf(
				accPlugins.getExpandedPane());
	}

}
