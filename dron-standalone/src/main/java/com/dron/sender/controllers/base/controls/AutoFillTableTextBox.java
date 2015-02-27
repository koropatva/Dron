package com.dron.sender.controllers.base.controls;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public class AutoFillTableTextBox<T> extends AutoFillTextBoxBase<T> {

	private ObservableList<T> itemList;

	private List<ChangeListener<T>> listeners = new ArrayList<ChangeListener<T>>();

	public void addChangeListener(ChangeListener<T> newListener) {
		listeners.add(newListener);
	}

	private void notifyListeners(ObservableValue<? extends T> object,
			T oldValue, T newValue) {
		for (ChangeListener<T> listener : listeners) {
			listener.changed(object, oldValue, newValue);
		}
	}

	public AutoFillTableTextBox(ObservableList<T> itemList) {
		this.itemList = itemList;
	}

	@Override
	public ObservableList<T> searchItems(final T value) {
		return itemList;
	}

	@Override
	public void onSelectAction(T fileName) {
		textField.setText(converter.toString(fileName));
		notifyListeners(null, fileName, fileName);
	}

}
