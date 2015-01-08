package com.dron.sender.controllers.base.controls;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

import org.apache.commons.lang3.StringUtils;

public abstract class BasePropertyTableView<T> {

	protected abstract int getMinWidth();

	protected abstract String getKeyName();

	protected abstract String getKey(T row);

	protected abstract String getValueName();

	protected abstract String getValue(T row);

	protected abstract boolean isEmpty(T row);

	protected abstract T getEmptyInstance();

	protected abstract void updateKey(T row, String newKey);

	protected abstract void updateValue(T row, String newValue);

	@SuppressWarnings("unchecked")
	public TableView<T> initialize(final ObservableList<T> observableList,
			final TableView<T> tableView) {
		final TableColumn<T, String> headerCol = initKeyColumn(observableList,
				tableView);

		final TableColumn<T, String> valueCol = initValueColumn(observableList,
				tableView);
		tableView.getColumns().setAll(headerCol, valueCol);
		tableView.setItems(observableList);
		tableView.setEditable(true);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.managedProperty().bind(tableView.visibleProperty());

		return tableView;
	}

	private TableColumn<T, String> initKeyColumn(
			final ObservableList<T> observableList, final TableView<T> tableView) {
		final TableColumn<T, String> headerCol = new TableColumn<>(getKeyName());
		headerCol.setCellValueFactory(new PropertyValueFactory<>(getKeyName()));
		headerCol.setMinWidth(getMinWidth());
		headerCol.setCellFactory(factory -> new TextFieldTableCell<T, String>(
				new DefaultStringConverter()));
		headerCol.setOnEditCommit(header -> {
			if (header != null && isEmpty(header.getRowValue())) {
				observableList.add(getEmptyInstance());
			}
			if (shouldRemoveHeader(observableList, header)) {
				observableList.remove(header.getRowValue());
			} else {
				updateKey(header.getRowValue(), header.getNewValue());
			}
		});

		return headerCol;
	}

	private TableColumn<T, String> initValueColumn(
			final ObservableList<T> observableList, final TableView<T> tableView) {
		final TableColumn<T, String> valueCol = new TableColumn<>(
				getValueName());
		valueCol.setCellValueFactory(new PropertyValueFactory<>(getValueName()));

		valueCol.setMinWidth(getMinWidth());
		valueCol.setCellFactory(factory -> new TextFieldTableCell<T, String>(
				new DefaultStringConverter()));
		valueCol.setOnEditCommit(value -> {
			if (value != null && isEmpty(value.getRowValue())) {
				observableList.add(getEmptyInstance());
			}
			if (shouldRemoveHeader(observableList, value)) {
				observableList.remove(value.getRowValue());
			} else {
				updateValue(value.getRowValue(), value.getNewValue());
			}
		});
		return valueCol;
	}

	private boolean shouldRemoveHeader(final ObservableList<T> observableList,
			CellEditEvent<T, String> header) {
		int index = observableList.indexOf(header.getRowValue());
		return header != null
				&& index >= 0
				&& StringUtils.isBlank(header.getNewValue())
				&& (getKey(observableList.get(index)).equals(
						header.getOldValue())
						&& StringUtils.isBlank(getValue(observableList
								.get(index))) || getValue(
						observableList.get(index)).equals(header.getOldValue())
						&& StringUtils
								.isBlank(getKey(observableList.get(index))));
	}

}
