package com.dron.sender.controllers.root.strategies;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
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
public class FillUIPluginAccordionStrategy extends BaseRootController
		implements IControllerStrategy {

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

	private AnchorPane createAnchorPane(final UIPlugin uiPlugin,
			final RootController controller) {
		final ObservableList<String> keys = FXCollections.observableArrayList();
		uiSequence.getUIParams().forEach(param -> {
			keys.add(param.getKey());
		});

		TableView<UIFutureParam> tblFutureParams = new TableView<>();
		tblFutureParams.setPrefWidth(DEFAULT_ACCORDION_WIDTH);
		tblFutureParams = new FutureParamTableView().initializeWithKeyList(
				uiPlugin.getFutureParams(), keys, tblFutureParams);

		TextField tfPluginName = new TextField();
		tfPluginName.setPrefWidth(DEFAULT_ACCORDION_WIDTH);
		tfPluginName.textProperty().bindBidirectional(uiPlugin.getName());

		Button btnRemove = new Button("Remove");
		btnRemove
				.setOnAction(listener -> {
					int expantedIndex = getExpantedUIPluginIndex();
					uiSequence.getUiPlugins().remove(
							uiSequence.getUiPlugins().get(expantedIndex));

					context.execute(
							controller,
							ControllerActionStrategy.ROOT_FILL_UI_PLUGIN_ACCORDION);
					uiSequence.selectedUIPLugin(expantedIndex - 1, context,
							controller);
				});

		AnchorPane anchorPane = new AnchorPane();
		anchorPane.getChildren().addAll(
				new VBox(tfPluginName, tblFutureParams, btnRemove));
		anchorPane.setOnDragDetected(event -> {
			System.out.println("anchorPane.setOnDragDetected "
					+ getExpantedUIPluginIndex());
			Dragboard db = anchorPane.startDragAndDrop(TransferMode.ANY);

			/* Put a string on a dragboard */
			ClipboardContent content = new ClipboardContent();
			content.putString(Integer.toString(getExpantedUIPluginIndex()));
			db.setContent(content);

			event.consume();
		});
		anchorPane.setOnDragOver(event -> {
			System.out.println("anchorPane.setOnDragOver "
					+ getExpantedUIPluginIndex());
			/* data is dragged over the target */
			/*
			 * accept it only if it is not dragged from the same node and if it
			 * has a string data
			 */
			if (event.getGestureSource() != anchorPane
					&& event.getDragboard().hasString()) {
				/* allow for both copying and moving, whatever user chooses */
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}

			event.consume();
		});
		anchorPane.setOnDragEntered(event -> {
			System.out.println("anchorPane.setOnDragEntered "
					+ getExpantedUIPluginIndex());
			/* the drag-and-drop gesture entered the target */
			/* show to the user that it is an actual gesture target */
			if (event.getGestureSource() != anchorPane
					&& event.getDragboard().hasString()) {
				anchorPane.setStyle("-fx-background-color: DAE6F3;");
			}

			event.consume();
		});

		anchorPane.setOnDragExited(event -> {
			System.out.println("anchorPane.setOnDragExited "
					+ getExpantedUIPluginIndex());
			/* mouse moved away, remove the graphical cues */
			anchorPane.setStyle("");

			event.consume();
		});

		anchorPane.setOnDragDropped(event -> {
			System.out.println("anchorPane.setOnDragDropped "
					+ getExpantedUIPluginIndex());
			/* data dropped */
			/* if there is a string data on dragboard, read it and use it */
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasString()) {
				tfPluginName.setText(db.getString());
				success = true;
			}
			/*
			 * let the source know whether the string was successfully
			 * transferred and used
			 */
			event.setDropCompleted(success);

			event.consume();
		});

		return anchorPane;
	}

	private int getExpantedUIPluginIndex() {
		return accPlugins.getChildrenUnmodifiable().indexOf(
				accPlugins.getExpandedPane());
	}

}
