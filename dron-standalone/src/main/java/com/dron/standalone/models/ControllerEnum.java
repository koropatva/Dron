package com.dron.standalone.models;

public enum ControllerEnum {
	ROOT("rootController");

	private String controllerName;

	private ControllerEnum(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getControllerName() {
		return controllerName;
	}

}