package com.dron.sender.ui.models;

import org.springframework.http.HttpMethod;

public class UIHttpMethod {

	public UIHttpMethod() {
	}

	public UIHttpMethod(HttpMethod method) {
		this.method = method;
	}

	private HttpMethod method;

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return method.name();
	}
}
