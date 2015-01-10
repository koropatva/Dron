package com.dron.sender.controllers.root.models;

public class UIHttpMethod {

	public UIHttpMethod() {
	}

	public UIHttpMethod(String method) {
		this.method = method;
	}

	private String method;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return method;
	}
	
}
