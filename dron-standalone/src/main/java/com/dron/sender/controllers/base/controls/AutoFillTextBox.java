package com.dron.sender.controllers.base.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * This class is main Control class which extends from Control <br>
 * and also implements basic functions of the AutoFillTextBoxFactory<br>
 *
 * You can easily utilize the AutoFillTextBox in your application<br>
 *
 * e.g <br>
 * 
 * @author Narayan G. Maharjan
 * @see <a href="http://www.blog.ngopal.com.np"> Blog </a>
 *
 */
public class AutoFillTextBox extends Control {

	/**
	 * Default pref width for base TextField and ListView
	 */
	static final double DEFAULT_PREF_WIDTH = 100.0d;

	/**
	 * Default pref height for base TextField and ListView
	 */
	static final double DEFAULT_PREF_HEIGHT = 26.0d;

	/**
	 * Default limit count in the ListView
	 */
	static final int DEFAULT_LIMIT_COUNT = 3;

	/**
	 * Base control
	 */
	private TextField textBox;

	/**
	 * ListView for show for customer with values
	 */
	private ListView<String> listview;

	/**
	 * List of data for autocomplete
	 */
	private ObservableList<String> data;

	public AutoFillTextBox(ObservableList<String> data) {
		setUp();
		this.data = data;
		FXCollections.sort(data);
	}

	private void setUp() {
		getStyleClass().setAll("autofill-text");
		textBox = new TextField();
		listview = new ListView<String>();

		listen();
	}

	private void listen() {
		this.prefHeightProperty().addListener(
				(bservable, oldValue, newValue) -> {
					textBox.setPrefHeight(newValue.doubleValue());
				});
		this.prefWidthProperty().addListener(
				(bservable, oldValue, newValue) -> {
					textBox.setPrefWidth(newValue.doubleValue());
				});
		this.minHeightProperty().addListener(
				(bservable, oldValue, newValue) -> {
					textBox.setMinHeight(newValue.doubleValue());
				});
		this.maxHeightProperty().addListener(
				(bservable, oldValue, newValue) -> {
					textBox.setMaxHeight(newValue.doubleValue());
				});
		this.minWidthProperty().addListener(
				(bservable, oldValue, newValue) -> {
					textBox.setMinWidth(newValue.doubleValue());
				});
		this.maxWidthProperty().addListener(
				(bservable, oldValue, newValue) -> {
					textBox.setMaxWidth(newValue.doubleValue());
				});
	}

	@Override
	public void requestFocus() {
		super.requestFocus();
		textBox.requestFocus();
	}

	@Override
	public void setMinSize(double d, double d1) {
		super.setMinSize(d, d1);
		textBox.setMinSize(d, d1);
	}

	@Override
	public void setPrefSize(double d, double d1) {
		super.setPrefSize(d, d1);
		textBox.setPrefSize(d, d1);
	}

	@Override
	public void resize(double d, double d1) {
		super.resize(d, d1);
		textBox.resize(d, d1);
	}

	@Override
	public void setMaxSize(double d, double d1) {
		super.setMaxSize(d, d1);
		textBox.setMaxSize(d, d1);
	}

	@Override
	protected double computeMaxHeight(double width) {
		return Math.max(DEFAULT_PREF_HEIGHT, textBox.getHeight());
	}

	@Override
	protected double computePrefHeight(double width) {
		return Math.max(DEFAULT_PREF_HEIGHT, textBox.getPrefHeight());
	}

	@Override
	protected double computeMinHeight(double width) {
		return Math.max(DEFAULT_PREF_HEIGHT, textBox.getPrefHeight());
	}

	@Override
	protected double computePrefWidth(double height) {
		return Math.max(DEFAULT_PREF_WIDTH, textBox.getPrefWidth());
	}

	@Override
	protected double computeMaxWidth(double height) {
		return Math.max(DEFAULT_PREF_WIDTH, textBox.getPrefWidth());
	}

	@Override
	protected double computeMinWidth(double height) {
		return Math.max(DEFAULT_PREF_WIDTH, textBox.getPrefWidth());
	}

	public ObservableList<String> getData() {
		return data;
	}

	public ListView<String> getListview() {
		return listview;
	}

	public TextField getTextBox() {
		return textBox;
	}

}