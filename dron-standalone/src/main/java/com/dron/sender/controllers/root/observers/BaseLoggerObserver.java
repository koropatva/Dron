package com.dron.sender.controllers.root.observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import com.dron.sender.pattern.interfaces.IBaseObserver;

public class BaseLoggerObserver implements PropertyChangeListener {

	private TextArea txaLogger;

	private String property;

	public BaseLoggerObserver(IBaseObserver observer, TextArea txaLogger) {
		this(observer, txaLogger, null);
	}

	public BaseLoggerObserver(IBaseObserver observer, TextArea txaLogger,
			String property) {
		observer.addChangeListener(this);
		this.txaLogger = txaLogger;
		this.property = property;
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (property == null || evt.getPropertyName().equals(property)) {
			Platform.runLater(() -> {
				txaLogger.setText(txaLogger.getText() + "\n"
						+ evt.getNewValue());
			});
		}
	}
}
