package com.dron.sender.controllers.root.models;

import org.springframework.http.HttpMethod;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UIPlugin {

	public UIPlugin() {
		method.set(HttpMethod.GET.name());
		headersList.add(new UIHttpHeaders());
		futureParams.add(new UIFutureParam());
	}

	protected final StringProperty url = new SimpleStringProperty();

	protected final StringProperty postBody = new SimpleStringProperty();

	protected final StringProperty method = new SimpleStringProperty();

	protected final StringProperty name = new SimpleStringProperty();

	protected final ObservableList<UIHttpHeaders> headersList = FXCollections
			.observableArrayList();

	protected final ObservableList<UIFutureParam> futureParams = FXCollections
			.observableArrayList();

	public ObservableList<UIHttpHeaders> getHeadersList() {
		return headersList;
	}

	public ObservableList<UIFutureParam> getFutureParams() {
		return futureParams;
	}

	public StringProperty getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url.set(url);
	}

	public StringProperty getPostBody() {
		return postBody;
	}

	public void setPostBody(String postBody) {
		this.postBody.set(postBody);
	}

	public StringProperty getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method.set(method);
	}

	public StringProperty getName() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

}
