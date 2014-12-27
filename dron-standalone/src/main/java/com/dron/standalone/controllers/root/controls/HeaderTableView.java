package com.dron.standalone.controllers.root.controls;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

import org.apache.commons.lang3.StringUtils;

import com.dron.standalone.models.UIHttpHeaders;

public class HeaderTableView {

	private static final int TABLE_PROPERTY_WIDTH = 120;

	@SuppressWarnings("unchecked")
	public static TableView<UIHttpHeaders> initialize(
			final ObservableList<UIHttpHeaders> observableList,
			final TableView<UIHttpHeaders> tableView) {
		final TableColumn<UIHttpHeaders, String> headerCol = initHeaderColumn(
				observableList, tableView);

		final TableColumn<UIHttpHeaders, String> valueCol = initValueColumn(
				observableList, tableView);
		tableView.getColumns().setAll(headerCol, valueCol);
		tableView.setItems(observableList);
		tableView.setEditable(true);
		tableView.setPrefHeight(RootConfig.getHeadersHeight());
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.managedProperty().bind(tableView.visibleProperty());

		return tableView;
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
