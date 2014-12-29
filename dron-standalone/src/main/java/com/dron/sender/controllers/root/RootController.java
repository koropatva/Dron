package com.dron.sender.controllers.root;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.dron.sender.actions.interfaces.IStageService;
import com.dron.sender.controllers.base.BaseController;
import com.dron.sender.controllers.root.controls.HeaderTableView;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.observers.BaseLoggerObserver;
import com.dron.sender.controllers.root.tasks.SequenceTask;
import com.dron.sender.models.ControllerEnum;
import com.dron.sender.sequence.models.Param;
import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;
import com.dron.sender.ui.models.UIHttpHeaders;
import com.dron.sender.ui.models.UIHttpMethod;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RootController extends BaseController implements Initializable {

	@Autowired
	private IStageService iStageService;

	@FXML
	private Button btnSend;

	@FXML
	private TextArea txaResponce;

	@FXML
	private StringProperty url;

	@FXML
	private StringProperty postBody;

	@FXML
	private TableView<UIHttpHeaders> tableView;

	@FXML
	private ToggleButton tbtnHeaders;

	@FXML
	private TextArea txaPostBody;

	@FXML
	private ChoiceBox<UIHttpMethod> cbMethods;

	private Sequence sequence;

	private final ObservableList<UIHttpHeaders> observableList = FXCollections
			.observableArrayList();

	@Override
	public void initialize(final URL arg0, final ResourceBundle arg1) {
		observableList.addAll(new UIHttpHeaders("Content-type",
				MediaType.APPLICATION_JSON_VALUE), new UIHttpHeaders("Accept",
				MediaType.APPLICATION_JSON_VALUE), new UIHttpHeaders("", ""));

		tableView = HeaderTableView.initialize(observableList, tableView);

		txaPostBody.managedProperty().bind(txaPostBody.visibleProperty());

		cbMethods.getItems().addAll(new UIHttpMethod(HttpMethod.GET),
				new UIHttpMethod(HttpMethod.POST),
				new UIHttpMethod(HttpMethod.PUT),
				new UIHttpMethod(HttpMethod.DELETE));
		cbMethods.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldChoice, newChoise) -> {
					cbMethods.getSelectionModel().select(newChoise);
					RootConfig.bindPostBody(txaPostBody, newChoise);

					updateControls();
				});
		cbMethods.getSelectionModel().select(0);

		tbtnHeaders.setOnAction(event -> {
			tableView.setVisible(!tableView.isVisible());
			RootConfig.bindHeaders(tableView.isVisible());

			updateControls();
		});

		updateControls();
		readSequence("/Users/admin/Documents/dron-project/dron/src/main/resources/json/Test.json");
	}

	private void updateControls() {
		tableView.setPrefHeight(RootConfig.getHeadersHeight());
		txaPostBody.setPrefHeight(RootConfig.getPostBodyHeight());
		txaResponce.setPrefHeight(RootConfig.getResponceHeight());
	}

	private void readSequence(String path) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			sequence = mapper.readValue(new File(path), Sequence.class);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void fillControllerComponents(int pluginNumber){
		Plugin plugin = sequence.getPlugins().get(pluginNumber);
	}
	
	@Override
	protected ControllerEnum getControllerEnum() {
		return ControllerEnum.ROOT;
	}

	@FXML
	protected void send(final ActionEvent actionEvent) {
		initLogging(sequence);

		SequenceTask sequenceTask = new SequenceTask(sequence);
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
