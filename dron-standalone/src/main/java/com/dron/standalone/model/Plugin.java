package com.dron.standalone.model;

import javafx.beans.property.StringProperty;

public class Plugin {
	
	private StringProperty url;

	public StringProperty getUrl() {
		return url;
	}

	public void setUrl(StringProperty url) {
		this.url = url;
	}
}
