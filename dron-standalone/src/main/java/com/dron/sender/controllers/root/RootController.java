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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.dron.sender.actions.interfaces.IStageService;
import com.dron.sender.controllers.base.BaseController;
import com.dron.sender.controllers.root.controls.HeaderTableView;
import com.dron.sender.controllers.root.controls.ParamTableView;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.models.UIHttpHeaders;
import com.dron.sender.controllers.root.models.UIHttpMethod;
import com.dron.sender.controllers.root.models.UIParam;
import com.dron.sender.controllers.root.observers.BaseLoggerObserver;
import com.dron.sender.controllers.root.service.UISequenceService;
import com.dron.sender.controllers.root.tasks.SequenceTask;
import com.dron.sender.models.ControllerEnum;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.transformers.TransformerFactory;
import com.dron.sender.sequence.models.Param;
import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RootController extends BaseController implements Initializable {

	@Autowired
	private IStageService iStageService;

	@FXML
	private TextField tfUrl;

	@FXML
	private TextField tfSequenceName;

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
	private ChoiceBox<UIHttpMethod> cbMethods;

	@FXML
	private Button btnSend;

	@FXML
	private Accordion accPlugins;

	private UISequenceService uiSequence;

	@Override
	protected ControllerEnum getControllerEnum() {
		return ControllerEnum.ROOT;
	}

	@Override
	public void initialize(final URL url, final ResourceBundle resource) {
		uiSequence = new UISequenceService(tfUrl, txaPostBody, cbMethods);

		TitledPane emptyPane = new TitledPane("Plugins", new AnchorPane());
		accPlugins.getPanes().add(emptyPane);
		accPlugins.setExpandedPane(emptyPane);

		tblHeaders = new HeaderTableView().initialize(uiSequence.getUiPlugin()
				.getHeadersList(), tblHeaders);

		tblParams = new ParamTableView().initialize(uiSequence.getParams(),
				tblParams);

		txaPostBody.managedProperty().bind(txaPostBody.visibleProperty());
		txaPostBody.setEditable(true);

		cbMethods.getItems().addAll(new UIHttpMethod(""),
				new UIHttpMethod(HttpMethod.GET.name()),
				new UIHttpMethod(HttpMethod.POST.name()),
				new UIHttpMethod(HttpMethod.PUT.name()),
				new UIHttpMethod(HttpMethod.DELETE.name()));
		cbMethods.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldChoice, newChoise) -> {
					cbMethods.getSelectionModel().select(newChoise);
					RootConfig.bindPostBody(txaPostBody, newChoise);

					updateControls();
				});
		cbMethods.getSelectionModel().select(0);

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
	protected void prepareSequence() {
		readSequence("/Users/admin/Documents/dron-project/dron/src/main/resources/json/"
				+ tfSequenceName.getText() + ".json");
	}

	private void readSequence(String path) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Sequence sequence = mapper
					.readValue(new File(path), Sequence.class);
			uiSequence.fillSequence(sequence);

			fillAccordion();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private void fillAccordion() {
		List<TitledPane> titledPanes = new ArrayList<TitledPane>();

		TransformerFactory.transformEntity(uiSequence.getMapFutureParams(),
				titledPanes, TransformKey.FILL_TITLED_PANES_WITH_FUTURE_PARAMS);

		accPlugins.getPanes().clear();
		accPlugins.getPanes().addAll(titledPanes);
		accPlugins.setExpandedPane(accPlugins.getPanes().get(0));
		accPlugins.expandedPaneProperty().addListener(
				(ObservableValue<? extends TitledPane> observable,
						TitledPane oldValue, TitledPane newValue) -> {
					System.out.println(newValue);
				});
	}

	@FXML
	protected void send(final ActionEvent actionEvent) {
		initLogging(uiSequence.getSequence());

		SequenceTask sequenceTask = new SequenceTask(uiSequence.getSequence());
		sequenceTask.start();
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
