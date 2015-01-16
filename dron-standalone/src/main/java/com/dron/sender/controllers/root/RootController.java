package com.dron.sender.controllers.root;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.BaseController;
import com.dron.sender.controllers.base.interfaces.IStageService;
import com.dron.sender.controllers.base.models.ControllerEnum;
import com.dron.sender.controllers.root.controls.FutureParamTableView;
import com.dron.sender.controllers.root.controls.HeaderTableView;
import com.dron.sender.controllers.root.controls.ParamTableView;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.models.RootUIPlugin;
import com.dron.sender.controllers.root.models.UIFutureParam;
import com.dron.sender.controllers.root.models.UIHttpHeaders;
import com.dron.sender.controllers.root.models.UIParam;
import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.controllers.root.models.UISequence;
import com.dron.sender.controllers.root.tasks.SequenceTask;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.observers.BaseLoggerObserver;
import com.dron.sender.pattern.services.transformers.TransformerFactory;
import com.dron.sender.sequence.models.Param;
import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RootController extends BaseController implements Initializable {

	private static final double DEFAULT_ACCORDION_WIDTH = 300.0;

	private static final int DEFAULT_SELECTED_HTTP_METHOD = 0;

	private static final int DEFAULT_SELECTED_UI_PLUGIN = 0;

	@Autowired
	private IStageService iStageService;

	@FXML
	private TextField tfUrl;

	@FXML
	private TextField tfSequenceName;

	@FXML
	private TextField tfNewPluginName;

	@FXML
	private TextArea txaResponce;

	@FXML
	private TableView<UIHttpHeaders> tblHeaders;

	@FXML
	private TableView<UIParam> tblParams;

	@FXML
	private HBox hbHeadersParamsTable;

	@FXML
	private ToggleButton tbtnHeaders;

	@FXML
	private ToggleButton tbtnParams;

	@FXML
	private TextArea txaPostBody;

	@FXML
	private ChoiceBox<String> cbMethods;

	@FXML
	private Accordion accPlugins;

	private UISequence uiSequence;

	private RootUIPlugin rootUiPlugin;

	@Override
	protected ControllerEnum getControllerEnum() {
		return ControllerEnum.ROOT;
	}

	@Override
	public void initialize(final URL url, final ResourceBundle resource) {
		uiSequence = new UISequence(tfSequenceName);
		rootUiPlugin = new RootUIPlugin(tfUrl, txaPostBody, cbMethods);

		createNewSequence();

		tblHeaders = new HeaderTableView().initialize(
				rootUiPlugin.getHeadersList(), tblHeaders);

		tblParams = new ParamTableView().initialize(uiSequence.getUIParams(),
				tblParams);

		txaPostBody.managedProperty().bind(txaPostBody.visibleProperty());
		txaPostBody.setEditable(true);

		cbMethods.getItems().addAll(HttpMethod.GET.name(),
				HttpMethod.POST.name(), HttpMethod.PUT.name(),
				HttpMethod.DELETE.name());
		cbMethods.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldChoice, newChoise) -> {
					cbMethods.getSelectionModel().select(newChoise);
					RootConfig.bindPostBody(txaPostBody, newChoise);

					updateControls();
				});
		cbMethods.getSelectionModel().select(DEFAULT_SELECTED_HTTP_METHOD);

		tbtnHeaders.setOnAction(event -> {
			tblHeaders.setVisible(!tblHeaders.isVisible());
			RootConfig.bindHeaders(tblHeaders.isVisible()
					|| tblParams.isVisible());

			updateControls();
		});

		tbtnParams.setOnAction(event -> {
			tblParams.setVisible(!tblParams.isVisible());
			RootConfig.bindHeaders(tblHeaders.isVisible()
					|| tblParams.isVisible());

			updateControls();
		});

		updateControls();
	}

	private void updateControls() {
		hbHeadersParamsTable.setPrefHeight(RootConfig
				.getHbHeadersParamsHeight());
		txaPostBody.setPrefHeight(RootConfig.getPostBodyHeight());
		txaResponce.setPrefHeight(RootConfig.getResponceHeight());
	}

	@FXML
	protected void createNewSequence() {
		uiSequence.getUIParams().clear();
		uiSequence.getUiPlugins().clear();
		uiSequence.getName().set("");

		rootUiPlugin.getFutureParams().clear();
		rootUiPlugin.getHeadersList().clear();
		rootUiPlugin.getMethod().set(HttpMethod.GET.name());
		rootUiPlugin.getName().set("");
		rootUiPlugin.getPostBody().set("");
		rootUiPlugin.getUrl().set("");

		fillAccordion();
	}

	@FXML
	protected void prepareSequence() {
		readSequence("/Users/admin/Documents/dron-project/dron/src/main/resources/json/"
				+ tfSequenceName.getText() + ".json");
	}

	private void readSequence(String path) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Sequence sequence = mapper
					.readValue(new File(path), Sequence.class);

			fillUiSequence(sequence);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private void fillUiSequence(Sequence sequence) {
		uiSequence.getUIParams().clear();
		uiSequence.getUiPlugins().clear();
		TransformerFactory.transformEntity(sequence, uiSequence,
				TransformKey.SEQUENCE);

		TransformerFactory.transformEntity(
				uiSequence.getUiPlugins().get(DEFAULT_SELECTED_UI_PLUGIN),
				rootUiPlugin, TransformKey.ROOT_UI_PLUGIN);

		fillAccordion();
	}

	private void fillAccordion() {
		List<TitledPane> titledPanes = new ArrayList<TitledPane>();

		if (uiSequence.getUiPlugins().isEmpty()) {
			uiSequence.getUiPlugins().add(new UIPlugin());
		}
		uiSequence.getUiPlugins().forEach(uiPlugin -> {
			titledPanes.add(createPluginTitlePane(uiPlugin));
		});

		accPlugins.getPanes().clear();
		accPlugins.getPanes().addAll(titledPanes);
		accPlugins.setExpandedPane(accPlugins.getPanes().get(
				DEFAULT_SELECTED_UI_PLUGIN));
		accPlugins.expandedPaneProperty().addListener(
				(ObservableValue<? extends TitledPane> observable,
						TitledPane oldValue, TitledPane newValue) -> {
					if (oldValue != null
							&& uiSequence.getUiPlugins().size() > accPlugins
									.getChildrenUnmodifiable()
									.indexOf(oldValue)) {
						TransformerFactory.reverseTransformEntity(
								uiSequence.getUiPlugins().get(
										accPlugins.getChildrenUnmodifiable()
												.indexOf(oldValue)),
								rootUiPlugin, TransformKey.ROOT_UI_PLUGIN);
					}
					if (newValue != null
							&& uiSequence.getUiPlugins().size() > accPlugins
									.getChildrenUnmodifiable()
									.indexOf(newValue)) {
						TransformerFactory.transformEntity(
								uiSequence.getUiPlugins().get(
										accPlugins.getChildrenUnmodifiable()
												.indexOf(newValue)),
								rootUiPlugin, TransformKey.ROOT_UI_PLUGIN);
					}
				});
	}

	private TitledPane createPluginTitlePane(UIPlugin uiPlugin) {
		AnchorPane anchorPane = new AnchorPane();

		TableView<UIFutureParam> tblFutureParams = new TableView<>();
		tblFutureParams.setPrefWidth(DEFAULT_ACCORDION_WIDTH);
		tblFutureParams = new FutureParamTableView().initialize(
				uiPlugin.getFutureParams(), tblFutureParams);

		TextField tfPluginName = new TextField();
		tfPluginName.setPrefHeight(16.0);
		tfPluginName.setPrefWidth(DEFAULT_ACCORDION_WIDTH);
		tfPluginName.textProperty().bindBidirectional(uiPlugin.getName());

		Button btnRemove = new Button("Remove");
		btnRemove.setOnAction(listener -> {
			int expantedIndex = getExpantedUIPluginIndex();
			uiSequence.getUiPlugins().remove(
					uiSequence.getUiPlugins().get(expantedIndex));

			fillAccordion();
			if (expantedIndex > 0) {
				accPlugins.setExpandedPane(accPlugins.getPanes().get(
						expantedIndex - 1));
			}
		});

		anchorPane.getChildren().addAll(
				new VBox(tfPluginName, tblFutureParams, btnRemove));
		anchorPane.setPrefWidth(DEFAULT_ACCORDION_WIDTH);

		TitledPane pluginTitle = new TitledPane("", anchorPane);
		pluginTitle.textProperty().bindBidirectional(uiPlugin.getName());
		return pluginTitle;
	}

	private int getExpantedUIPluginIndex() {
		return accPlugins.getChildrenUnmodifiable().indexOf(
				accPlugins.getExpandedPane());
	}

	@FXML
	protected void send(final ActionEvent actionEvent) {
		Sequence sequence = fillRootSequence();
		initLogging(sequence);

		SequenceTask sequenceTask = new SequenceTask(sequence);
		sequenceTask.start();
	}

	private Sequence fillRootSequence() {
		Sequence sequence = new Sequence();
		TransformerFactory.reverseTransformEntity(sequence, uiSequence,
				TransformKey.SEQUENCE);
		return sequence;
	}

	@FXML
	protected void addNewPlugin(final ActionEvent event) {
		UIPlugin uiPlugin = new UIPlugin();
		uiPlugin.setName(tfNewPluginName.getText());
		uiSequence.getUiPlugins().add(uiPlugin);

		tfNewPluginName.setText("");
		fillAccordion();
		accPlugins.setExpandedPane(accPlugins.getPanes().get(
				accPlugins.getPanes().size() - 1));
	}

	private void initLogging(Sequence sequence) {
		for (Plugin plugin : sequence.getPlugins()) {
			new BaseLoggerObserver(plugin, txaResponce,
					Plugin.PROPERTY_RESPONCE);
		}

		for (Param param : sequence.getParams()) {
			new BaseLoggerObserver(param, txaResponce);
		}
	}

	@FXML
	private void swatSequenceScene(final ActionEvent event) {
		iStageService.showController(ControllerEnum.SEQUENCE);
	}

}
