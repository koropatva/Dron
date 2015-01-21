package com.dron.sender.controllers.base.models;

public enum ControllerEnum {
	ROOT("RootView", "rootController");

	private String controllerName;
	private String viewName;

	private ControllerEnum(String viewName, String controllerName) {
		this.controllerName = controllerName;
		this.viewName = viewName;
	}

	public String getControllerName() {
		return controllerName;
	}

	public String getViewName() {
		return viewName;
	}

}