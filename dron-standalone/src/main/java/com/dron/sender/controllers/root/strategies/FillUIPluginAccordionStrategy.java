package com.dron.sender.controllers.root.strategies;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.FutureParamTableView;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.models.BaseRootController;
import com.dron.sender.controllers.root.models.UIFutureParam;
import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;

@Component
public class FillUIPluginAccordionStrategy extends BaseRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_FILL_UI_PLUGIN_ACCORDION;
	}

	@Override
	public void execute(final IBaseController iBaseController) {
		final RootController controller = (RootController) iBaseController;
		setUp(controller);

		List<TitledPane> titledPanes = new ArrayList<TitledPane>();

		if (uiSequence.getUiPlugins().isEmpty()) {
			uiSequence.getUiPlugins().add(new UIPlugin());
		}

		uiSequence.getOrder().forEach(order -> {
			UIPlugin uiPlugin = uiSequence.findPlugin(order);
			titledPanes.add(createPluginTitlePane(uiPlugin, controller));
		});

		accPlugins.getPanes().clear();
		accPlugins.getPanes().addAll(titledPanes);
		accPlugins.setExpandedPane(accPlugins.getPanes().get(0));

		accPlugins.expandedPaneProperty().addListener(
				(ObservableValue<? extends TitledPane> observable,
						TitledPane oldValue, TitledPane newValue) -> {
					if (oldValue != null) {
						RootConfig.setDisableRootProperty(true);
					}
					if (newValue != null) {
						int index = accPlugins.getChildrenUnmodifiable()
								.indexOf(newValue);

						uiSequence.selectedUIPLugin(
								uiSequence.getOrder().get(index), context,
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

	private AnchorPane createAnchorPane(final UIPlugin uiPlugin,
			final RootController controller) {
		TableView<UIFutureParam> tblFutureParams = new TableView<>();
		tblFutureParams.setPrefWidth(DEFAULT_ACCORDION_WIDTH);
		tblFutureParams.setPrefHeight(DEFAULT_ACCORDION_HEIGHT);

		tblFutureParams = new FutureParamTableView().initializeWithKeyList(
				uiPlugin.getFutureParams(), uiSequence.getKeys(),
				tblFutureParams);

		TextField tfPluginName = new TextField();
		tfPluginName.setPrefWidth(DEFAULT_ACCORDION_WIDTH);
		tfPluginName.textProperty().bindBidirectional(uiPlugin.getName());

		int index = uiSequence.findSelectedIndex(uiPlugin.getId().get());
		Button btnMoveDown = null;
		if (index != uiSequence.getOrder().size() - 1) {
			btnMoveDown = new Button("Move down");
			btnMoveDown.setOnAction(listener -> {
				setUiPlugin(uiPlugin);
				context.execute(controller,
						ControllerActionStrategy.ROOT_MOVE_PLUGIN_DOWN);
			});
		}

		Button btnMoveUp = null;
		if (index != 0) {
			btnMoveUp = new Button("Move up");
			btnMoveUp.setOnAction(listener -> {
				setUiPlugin(uiPlugin);
				context.execute(controller,
						ControllerActionStrategy.ROOT_MOVE_PLUGIN_UP);
			});
		}
		Button btnRemove = new Button("Remove");
		btnRemove.setOnAction(listener -> {
			int expantedIndex = getExpantedUIPluginIndex();
			uiSequence.removePlugin(expantedIndex);

			context.execute(controller,
					ControllerActionStrategy.ROOT_FILL_UI_PLUGIN_ACCORDION);
			uiSequence.selectedUIPLugin(context, controller);
		});

		AnchorPane anchorPane = new AnchorPane();
		HBox hBox = new HBox(btnRemove);
		if (btnMoveDown != null) {
			hBox.getChildren().add(btnMoveDown);
		}
		if (btnMoveUp != null) {
			hBox.getChildren().add(btnMoveUp);
		}

		VBox vBox = new VBox(tfPluginName, tblFutureParams, hBox);
		anchorPane.getChildren().addAll(vBox);

		return anchorPane;
	}

	private int getExpantedUIPluginIndex() {
		return accPlugins.getChildrenUnmodifiable().indexOf(
				accPlugins.getExpandedPane());
	}

}
