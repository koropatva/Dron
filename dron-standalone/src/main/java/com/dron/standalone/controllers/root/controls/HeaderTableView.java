package com.dron.standalone.controllers.root.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import com.dron.standalone.models.UIHttpHeaders;

public class HeaderTableView {

	private static final int TABLE_PROPERTY_WIDTH = 150;

	private static final ObservableList<UIHttpHeaders> observableList = FXCollections
			.observableArrayList();

	@SuppressWarnings("unchecked")
	public static TableView<UIHttpHeaders> initialize(
			TableView<UIHttpHeaders> tableView) {
		observableList.addAll(new UIHttpHeaders("Content-type",
				MediaType.APPLICATION_JSON_VALUE), new UIHttpHeaders("Accept",
				MediaType.APPLICATION_JSON_VALUE), new UIHttpHeaders("", ""));
		final TableColumn<UIHttpHeaders, String> headerCol = initHeaderColumn(
				observableList, tableView);

		final TableColumn<UIHttpHeaders, String> valueCol = initValueColumn(
				observableList, tableView);
		tableView.getColumns().setAll(headerCol, valueCol);
		tableView.setItems(observableList);
		tableView.setEditable(true);
		tableView.setMaxHeight(170.0);
		tableView.setFixedCellSize(25.0);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.managedProperty().bind(tableView.visibleProperty());

		updateTableViewHeight(tableView);

		return tableView;
	}

	private static void updateTableViewHeight(TableView<UIHttpHeaders> tableView) {
		double newHeight = (observableList.size() + 2)
				* tableView.getFixedCellSize();
		tableView.setMinHeight(tableView.getMaxHeight() > newHeight ? newHeight
				: tableView.getMaxHeight());
	}

	private static TableColumn<UIHttpHeaders, String> initValueColumn(
			final ObservableList<UIHttpHeaders> observableList,
			final TableView<UIHttpHeaders> tableView) {
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
			updateTableViewHeight(tableView);
		});
		return valueCol;
	}

	private static TableColumn<UIHttpHeaders, String> initHeaderColumn(
			final ObservableList<UIHttpHeaders> observableList,
			final TableView<UIHttpHeaders> tableView) {
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
			updateTableViewHeight(tableView);
		});

		return headerCol;
	}

	private static boolean shouldRemoveHeader(
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

}
