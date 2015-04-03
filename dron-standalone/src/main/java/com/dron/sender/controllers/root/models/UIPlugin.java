package com.dron.sender.controllers.root.models;

import java.io.IOException;
import java.util.UUID;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UIPlugin implements Cloneable {

	private ObjectMapper mapper = new ObjectMapper();

	private StringProperty id = new SimpleStringProperty();

	private final StringProperty url = new SimpleStringProperty();

	private final StringProperty postBody = new SimpleStringProperty();

	private final StringProperty method = new SimpleStringProperty();

	private final StringProperty name = new SimpleStringProperty();

	private final ObservableList<UIHttpHeaders> headersList = FXCollections
			.observableArrayList();

	private final ObservableList<UIFutureParam> futureParams = FXCollections
			.observableArrayList();

	public UIPlugin() {
		prepareEmptyPlugin();
	}

	public void clear() {
		id.set(null);
		url.set("");
		postBody.set("");
		name.set("");
		headersList.clear();
		futureParams.clear();
	}

	public void prepareEmptyPlugin() {
		id.set(UUID.randomUUID().toString());
		method.set(HttpMethod.GET.name());
		headersList.add(new UIHttpHeaders());
		futureParams.add(new UIFutureParam());
	}

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
		if (postBody != null) {
			try {
				Object json = mapper.readValue(postBody, Object.class);
				postBody = mapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(json);
			} catch (IOException e) {
			}
		}
		this.postBody.set(postBody);
	}

	public StringProperty getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method.set(method);
	}

	public StringProperty getId() {
		return id;
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public StringProperty getName() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	@Override
	public UIPlugin clone() {
		UIPlugin plugin = new UIPlugin();
		plugin.setId(id.get());
		plugin.setMethod(method.get());
		plugin.setName(name.get());
		plugin.setPostBody(postBody.get());
		plugin.setUrl(url.get());
		headersList.forEach(header -> {
			plugin.getHeadersList().add(header.clone());
		});
		futureParams.forEach(futureParam -> {
			plugin.getFutureParams().add(futureParam.clone());
		});

		return plugin;
	}
}
