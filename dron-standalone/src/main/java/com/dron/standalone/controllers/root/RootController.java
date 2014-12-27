package com.dron.standalone.controllers.root;

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

import com.dron.standalone.actions.interfaces.IStageService;
import com.dron.standalone.controllers.base.BaseController;
import com.dron.standalone.controllers.root.controls.HeaderTableView;
import com.dron.standalone.controllers.root.controls.RootConfig;
import com.dron.standalone.models.ControllerEnum;
import com.dron.standalone.models.UIHttpHeaders;
import com.dron.standalone.models.UIHttpMethod;

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
	}

	private void updateControls() {
		tableView.setPrefHeight(RootConfig.getHeadersHeight());
		txaPostBody.setPrefHeight(RootConfig.getPostBodyHeight());
		txaResponce.setPrefHeight(RootConfig.getResponceHeight());
	}

	@Override
	protected ControllerEnum getControllerEnum() {
		return ControllerEnum.ROOT;
	}

	@FXML
	protected void send(final ActionEvent actionEvent) {
	}

	@FXML
	private void swatSequenceScene(final ActionEvent event) {
		iStageService.showController(ControllerEnum.SEQUENCE);
	}

}
