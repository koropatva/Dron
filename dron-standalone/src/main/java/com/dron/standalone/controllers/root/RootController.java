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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.dron.standalone.actions.interfaces.IStageService;
import com.dron.standalone.controllers.base.BaseController;
import com.dron.standalone.models.ControllerEnum;
import com.dron.standalone.models.UIHttpHeaders;

@Component
public class RootController extends BaseController implements Initializable {

	private static final int TABLE_PROPERTY_WIDTH = 150;

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

	final ObservableList<UIHttpHeaders> observableList = FXCollections
			.observableArrayList();

	@Override
	public void initialize(final URL arg0, final ResourceBundle arg1) {

		observableList.addAll(new UIHttpHeaders("Content-type",
				MediaType.APPLICATION_JSON_VALUE), new UIHttpHeaders("Accept",
				MediaType.APPLICATION_JSON_VALUE), new UIHttpHeaders("", ""));
		final TableColumn<UIHttpHeaders, String> headerCol = initHeaderColumn(observableList);

		final TableColumn<UIHttpHeaders, String> valueCol = initValueColumn(observableList);
		tableView.getColumns().setAll(headerCol, valueCol);
		tableView.setItems(observableList);
		tableView.setEditable(true);
		tableView.setMaxHeight(170.0);
		tableView.setFixedCellSize(25.0);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		updateTableViewHeight();
	}

	private void updateTableViewHeight() {
		double newHeight = (observableList.size() + 2)
				* tableView.getFixedCellSize();
		tableView.setMinHeight(tableView.getMaxHeight() > newHeight ? newHeight
				: tableView.getMaxHeight());
	}

	private TableColumn<UIHttpHeaders, String> initValueColumn(
			final ObservableList<UIHttpHeaders> observableList) {
		final TableColumn<UIHttpHeaders, String> valueCol = new TableColumn<>(
				UIHttpHeaders.PROPERTY_VALUE);
		valueCol.setCellValueFactory(new PropertyValueFactory<>(
				UIHttpHeaders.PROPERTY_VALUE));

		valueCol.setMinWidth(TABLE_PROPERTY_WIDTH);
		valueCol.setCellFactory(factory -> new TextFieldTableCell<UIHttpHeaders, String>(
				new DefaultStringConverter()));
		valueCol.setOnEditCommit(value -> {
			if (value != null && value.getRowValue().isEmpty()) {
				observableList.add(new UIHttpHeaders("", ""));
			}
			if (shouldRemoveHeader(observableList, value)) {
				observableList.remove(value.getRowValue());
			} else {
				value.getRowValue().setValue(value.getNewValue());
			}
			updateTableViewHeight();
		});
		return valueCol;
	}

	private TableColumn<UIHttpHeaders, String> initHeaderColumn(
			final ObservableList<UIHttpHeaders> observableList) {
		final TableColumn<UIHttpHeaders, String> headerCol = new TableColumn<>(
				UIHttpHeaders.PROPERTY_HEADER);
		headerCol.setCellValueFactory(new PropertyValueFactory<>(
				UIHttpHeaders.PROPERTY_HEADER));
		headerCol.setMinWidth(TABLE_PROPERTY_WIDTH);
		headerCol
				.setCellFactory(factory -> new TextFieldTableCell<UIHttpHeaders, String>(
						new DefaultStringConverter()));
		headerCol.setOnEditCommit(header -> {
			if (header != null && header.getRowValue().isEmpty()) {
				observableList.add(new UIHttpHeaders("", ""));
			}
			if (shouldRemoveHeader(observableList, header)) {
				observableList.remove(header.getRowValue());
			} else {
				header.getRowValue().setHeader(header.getNewValue());
			}
			updateTableViewHeight();
		});

		return headerCol;
	}

	private boolean shouldRemoveHeader(
			final ObservableList<UIHttpHeaders> observableList,
			CellEditEvent<UIHttpHeaders, String> header) {
		int index = observableList.indexOf(header.getRowValue());
		return header != null
				&& index > 0
				&& StringUtils.isBlank(header.getNewValue())
				&& (observableList.get(index).getHeader()
						.equals(header.getOldValue())
						&& StringUtils.isBlank(observableList.get(index)
								.getValue()) || observableList.get(index)
						.getValue().equals(header.getOldValue())
						&& StringUtils.isBlank(observableList.get(index)
								.getHeader()));
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
		tableView.getItems().forEach(
				action -> System.out.println(action.getValue()));

	}

	@FXML
	private void swatSequenceScene(final ActionEvent event) {
		iStageService.showController(ControllerEnum.SEQUENCE);
	}

}
