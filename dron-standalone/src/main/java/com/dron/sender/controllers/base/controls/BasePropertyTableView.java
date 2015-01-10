package com.dron.sender.controllers.base.controls;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

import org.apache.commons.lang3.StringUtils;

import com.dron.sender.controllers.base.interfaces.IModelTableView;

public abstract class BasePropertyTableView<T extends IModelTableView> {

	protected abstract int getMinWidth();

	protected abstract String getKeyName();

	protected abstract String getValueName();

	protected abstract T getEmptyInstance();

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
			if (header != null && header.getRowValue().isEmpty()) {
				observableList.add(getEmptyInstance());
			}
			if (shouldRemoveHeader(observableList, header)) {
				observableList.remove(header.getRowValue());
			} else {
				header.getRowValue().updateModelKey(header.getNewValue());
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
			if (value != null && value.getRowValue().isEmpty()) {
				observableList.add(getEmptyInstance());
			}
			if (shouldRemoveHeader(observableList, value)) {
				observableList.remove(value.getRowValue());
			} else {
				value.getRowValue().updateModelValue(value.getNewValue());
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
				&& (observableList.get(index).getModelKey()
						.equals(header.getOldValue())
						&& StringUtils.isBlank(observableList.get(index)
								.getModelValue()) || observableList.get(index)
						.getModelValue().equals(header.getOldValue())
						&& StringUtils.isBlank(observableList.get(index)
								.getModelKey()));
	}

}
