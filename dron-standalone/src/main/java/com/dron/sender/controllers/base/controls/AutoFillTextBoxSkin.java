/*

 *
 */
package com.dron.sender.controllers.base.controls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.stage.Window;

import org.apache.commons.lang3.StringUtils;

public class AutoFillTextBoxSkin extends SkinBase<AutoFillTextBox> implements
		ChangeListener<String> {

	private AutoFillTextBox autoFillTextBox;

	private Popup popup;

	private TextField textField;

	private ListView<String> listView;

	private String tmpText;

	public AutoFillTextBoxSkin(final AutoFillTextBox autoFillTextBox) {
		super(autoFillTextBox);

		this.autoFillTextBox = autoFillTextBox;
		this.listView = autoFillTextBox.getListview();
		this.textField = autoFillTextBox.getTextBox();

		listView.setOnMouseReleased((event) -> {
			selectList();
		});
		listView.setOnKeyReleased(event -> {
			switch (event.getCode()) {
				case ENTER:
					selectList();
					break;
				default:
					break;
			}
		});

		// This cell factory helps to know which cell has been selected so that
		// when ever any cell is selected the textbox rawText must be changed
		listView.setCellFactory(listView -> {
			// A simple ListCell containing only Label
			final ListCell<String> cell = new ListCell<String>() {
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					setText(item);
				}
			};

			// A listener to know which cell was selected so that the
			// textbox we can set the rawTextProperty of textbox
			cell.focusedProperty().addListener(observable -> {
				if (cell.getItem() != null && cell.isFocused()) {
					textField.textProperty().removeListener(this);
					textField.textProperty().setValue(tmpText);
					textField.textProperty().addListener(this);
					textField.selectRange(tmpText.length(), tmpText.length());
				}
			});

			return cell;
		});

		textField.setOnKeyPressed(event -> {
			// WHEN USER PRESS DOWN ARROW KEY FOCUS TRANSFER TO LISTVIEW
				switch (event.getCode()) {
					case BACK_SPACE:
					case DELETE:
						textField.requestFocus();
						popup.hide();
						break;
					default:
						break;
				}
			});
		textField.textProperty().addListener(this);

		textField.focusedProperty().addListener(
				(observable, oldValue, newValue) -> {
					if (newValue) {
						textField.requestFocus();
					}
				});

		popup = new Popup();
		popup.setAutoHide(true);
		popup.getContent().add(listView);

		// Adding textbox in this control Children
		getChildren().addAll(textField);
	}

	public Window getWindow() {
		return autoFillTextBox.getScene().getWindow();
	}

	private void selectList() {
		if (listView.getSelectionModel().getSelectedItem() != null) {
			textField.setText(listView.getSelectionModel().getSelectedItem());
			textField.requestFocus();
			textField.requestLayout();
			textField.end();
			listView.getItems().clear();
			popup.hide();
		}
	}

	/**
	 * A Popup containing Listview is trigged from this function This function
	 * automatically resize it's height and width according to the width of
	 * textbox and item's cell height
	 */
	public void showPopup() {
		listView.setPrefWidth(textField.getWidth());

		if (listView.getItems().size() > AutoFillTextBox.DEFAULT_LIMIT_COUNT) {
			listView.setPrefHeight(AutoFillTextBox.DEFAULT_LIMIT_COUNT
					* AutoFillTextBox.DEFAULT_PREF_HEIGHT);
		} else {
			listView.setPrefHeight(listView.getItems().size()
					* AutoFillTextBox.DEFAULT_PREF_HEIGHT);
		}

		// SHOWING THE POPUP JUST BELOW TEXTBOX
		popup.show(getWindow(),
				getWindow().getX() + textField.localToScene(0, 0).getX()
						+ textField.getScene().getX(), getWindow().getY()
						+ textField.localToScene(0, 0).getY()
						+ textField.getScene().getY()
						+ AutoFillTextBox.DEFAULT_PREF_HEIGHT);

		listView.getSelectionModel().clearSelection();
		listView.getFocusModel().focus(-1);
	}

	/**
	 * ********************************************* When ever the the
	 * rawTextProperty is changed then this listener is activated
	 *
	 * @param observable
	 * @param oldValue
	 * @param newValue
	 *********************************************** */
	@Override
	public void changed(ObservableValue<? extends String> observable,
			String oldValue, String newValue) {
		tmpText = newValue.trim();
		if (StringUtils.isNotBlank(newValue)) {
			ObservableList<String> list = FXCollections.observableArrayList();
			for (String dat : autoFillTextBox.getData()) {
				if (StringUtils.startsWithIgnoreCase(dat, newValue)) {
					list.add(dat);
					if (list.size() == AutoFillTextBox.DEFAULT_LIMIT_COUNT) {
						break;
					}
				}
				listView.setItems(list);
			}
		} else {
			listView.setItems(autoFillTextBox.getData());
		}
		showPopup();
	}
}