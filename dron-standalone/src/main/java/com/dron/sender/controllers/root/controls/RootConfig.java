package com.dron.sender.controllers.root.controls;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TextArea;

public class RootConfig {

	private static final double TXA_POST_BODY_HEIGHT = 300.0;

	private static BooleanProperty disableRootProperty = new SimpleBooleanProperty(
			false);

	private static DoubleProperty postBodyHeight = new SimpleDoubleProperty(
			TXA_POST_BODY_HEIGHT);

	public static void setDisableRootProperty(boolean disable) {
		RootConfig.disableRootProperty.set(disable);
	}

	public static void bindPostBody(TextArea txaPostBody, String method) {
		switch (method) {
			case "POST":
			case "PUT":
				txaPostBody.setVisible(true);
				postBodyHeight.set(TXA_POST_BODY_HEIGHT);
				break;
			default:
				txaPostBody.setVisible(false);
				postBodyHeight.set(-10);
				break;
		}
	}

	public static BooleanProperty getDisableRootProperty() {
		return disableRootProperty;
	}
}
