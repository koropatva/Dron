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
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.dron.standalone.actions.interfaces.IStageService;
import com.dron.standalone.controllers.base.BaseController;
import com.dron.standalone.controllers.root.controls.HeaderTableView;
import com.dron.standalone.models.ControllerEnum;
import com.dron.standalone.models.UIHttpHeaders;

@Component
public class RootController extends BaseController implements Initializable {

	@Autowired
	private IStageService iStageService;

	@FXML
	private Button btnSend;

	@FXML
	private TextArea txaLogger;

	@FXML
	private StringProperty url;

	@FXML
	private StringProperty postBody;

	@FXML
	private TableView<UIHttpHeaders> tableView;

	@FXML
	private ToggleButton tbtnHeaders;

	private final ObservableList<UIHttpHeaders> observableList = FXCollections
			.observableArrayList();

	@Override
	public void initialize(final URL arg0, final ResourceBundle arg1) {
		observableList.addAll(new UIHttpHeaders("Content-type",
				MediaType.APPLICATION_JSON_VALUE), new UIHttpHeaders("Accept",
				MediaType.APPLICATION_JSON_VALUE), new UIHttpHeaders("", ""));

		tableView = HeaderTableView.initialize(observableList, tableView);

		tbtnHeaders.setOnAction(event -> {
			tableView.setVisible(!tableView.isVisible());
		});
	}

	@Override
	protected ControllerEnum getControllerEnum() {
		return ControllerEnum.ROOT;
	}

	@FXML
	protected void send(final ActionEvent actionEvent) {
		// SequenceTask sequenceTask = new SequenceTask(txaLogger,
		// "/Users/admin/Documents/dron-project/dron/src/main/resources/json/Test.json");
		//
		// sequenceTask.start();
	}

	@FXML
	private void swatSequenceScene(final ActionEvent event) {
		iStageService.showController(ControllerEnum.SEQUENCE);
	}

}
