package com.dron.sender.controllers.base.controls;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

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
			header.getRowValue().updateModelKey(header.getNewValue());

			cleanList(observableList);

			observableList.add(getEmptyInstance());
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
			value.getRowValue().updateModelValue(value.getNewValue());

			cleanList(observableList);

			observableList.add(getEmptyInstance());
		});
		return valueCol;
	}

	private void cleanList(final ObservableList<T> observableList) {
		List<T> notEmptyList = new ArrayList<>();
		observableList.forEach(item -> {
			if (!item.isEmpty()) {
				notEmptyList.add(item);
			}
		});
		observableList.clear();
		observableList.addAll(notEmptyList);
	}

}
