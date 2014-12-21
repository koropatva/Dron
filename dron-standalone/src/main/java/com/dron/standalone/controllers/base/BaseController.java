package com.dron.standalone.controllers.base;

import com.dron.standalone.controllers.base.interfaces.IBaseController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public abstract class BaseController extends Pane implements IBaseController {

	private FXMLLoader loader;

	protected abstract String getViewName();

	protected BaseController() {

		// Get path for class location
		String viewPath = this.getClass().getPackage().getName()
				.replace('.', '/');

		loader = new FXMLLoader();
		loader.setLocation(BaseController.class.getResource("/" + viewPath
				+ "/" + getViewName() + ".fxml"));
	}

	public FXMLLoader getLoader() {
		return loader;
	}
}
