package com.dron.standalone.controllers.root;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import javafx.beans.property.MapProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.dron.standalone.actions.interfaces.IStageService;
import com.dron.standalone.controllers.base.BaseController;
import com.dron.standalone.controllers.root.tasks.SequenceTask;
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<UIHttpHeaders> httpHeaders = new ArrayList<UIHttpHeaders>();
		httpHeaders.add(new UIHttpHeaders("Content-type",
				MediaType.APPLICATION_JSON_VALUE));
		httpHeaders.add(new UIHttpHeaders("Accept",
				MediaType.APPLICATION_JSON_VALUE));

		ObservableList<UIHttpHeaders> observableList = FXCollections
				.observableList(httpHeaders);
		tableView.setItems(observableList);
		TableColumn<UIHttpHeaders, String> headerCol = new TableColumn<>(
				"header");
		headerCol.setCellValueFactory(new PropertyValueFactory<>("header"));

		TableColumn<UIHttpHeaders, String> valueCol = new TableColumn<>("value");
		valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

		tableView.getColumns().setAll(headerCol, valueCol);
		tableView.setEditable(true);
	}

	@Override
	protected ControllerEnum getControllerEnum() {
		return ControllerEnum.ROOT;
	}

	@FXML
	protected void send(ActionEvent actionEvent) {
//		SequenceTask sequenceTask = new SequenceTask(txaLogger,
//				"/Users/admin/Documents/dron-project/dron/src/main/resources/json/Test.json");
//
//		sequenceTask.start();
		
		tableView.setMinWidth(tableView.getMinWidth() + 300);
		tableView.setMinHeight(tableView.getMinHeight() + 200);
	}

	@FXML
	private void swatSequenceScene(ActionEvent event) {
		iStageService.showController(ControllerEnum.SEQUENCE);
	}

}
