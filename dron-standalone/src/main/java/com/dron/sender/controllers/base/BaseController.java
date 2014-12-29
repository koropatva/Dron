package com.dron.sender.controllers.base;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.models.ControllerEnum;

public abstract class BaseController extends Pane implements IBaseController {

	private FXMLLoader loader;

	protected abstract ControllerEnum getControllerEnum();

	protected BaseController() {

		// Get path for class location
		String viewPath = this.getClass().getPackage().getName()
				.replace('.', '/');

		loader = new FXMLLoader(BaseController.class.getResource("/" + viewPath
				+ "/" + getControllerEnum().getViewName() + ".fxml"));
		loader.setController(this);
	}

	public FXMLLoader getLoader() {
		return loader;
	}
}
