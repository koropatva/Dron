package com.dron.standalone.models;

public enum ControllerEnum {
	ROOT("RootView", "rootController"), SEQUENCE("SequenceView",
			"sequenceController");

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