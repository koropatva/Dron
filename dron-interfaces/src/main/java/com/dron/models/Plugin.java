package com.dron.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.dron.interfaces.IBaseObserver;
import com.dron.utils.ParamsUtils;

public class Plugin implements IBaseObserver {

	public static final String PROPERTY_SEQUENCE = "Sequence";

	public static final String PROPERTY_URL = "Url";

	public static final String PROPERTY_RESPONCE = "Responce";

	public static final String PROPERTY_POST_BODY = "PostBody";

	public static final String PROPERTY_HTTP_METHOD = "HttpMethod";

	public Plugin() {
	}

	public Plugin(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
		setHeaders(null);

		futureParams = new ArrayList<FutureParam>();
	}

	private List<PropertyChangeListener> listeners = new ArrayList<>();

	private void notifyListeners(Object object, String property,
			Object oldValue, Object newValue) {
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(object, property,
					oldValue, newValue));
		}
	}

	public void addChangeListener(PropertyChangeListener newListener) {
		listeners.add(newListener);
	}

	public void setHeaders(HttpHeaders headers) {
		if (headers == null) {
			headers = new HttpHeaders();
		}
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(mediaTypes);

		notifyListeners(this, "Headers", this.headers, this.headers = headers);
	}

	private Sequence sequence;

	private HttpHeaders headers;

	private HttpMethod httpMethod;

	private String url;

	private String postBody;

	private String request;

	private String responce;

	/**
	 * Structure of parameter way looks like: names of JSON object separated by
	 * dot. If you need to select someone object from the list you should to use
	 * "[numbers separated by comma]"
	 */
	private List<FutureParam> futureParams;

	public void addFutureParam(String key, String dependence) {
		sequence.getParams().add(new Param(key, null));
		this.futureParams.add(new FutureParam(key, dependence));
	}

	public HttpEntity<String> fillEntity() {
		return new HttpEntity<String>(ParamsUtils.fillDataParams(postBody,
				sequence.getParams()), headers);
	}

	public String fillUrl() {
		return ParamsUtils.fillDataParams(url, sequence.getParams());
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		notifyListeners(this, PROPERTY_HTTP_METHOD, this.httpMethod,
				this.httpMethod = httpMethod);
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public String getPostBody() {
		return postBody;
	}

	public void setPostBody(String postBody) {
		notifyListeners(this, PROPERTY_POST_BODY, this.postBody,
				this.postBody = postBody);
	}

	public String getResponce() {
		return responce;
	}

	public void setResponce(String responce) {
		notifyListeners(this, PROPERTY_RESPONCE, this.responce,
				this.responce = responce);
	}

	public String getRequest() {
		return request;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		notifyListeners(this, PROPERTY_URL, this.url, this.url = url);
	}

	public void setSequence(Sequence sequence) {
		notifyListeners(this, PROPERTY_SEQUENCE, this.sequence,
				this.sequence = sequence);
	}

	public List<FutureParam> getFutureParams() {
		return futureParams;
	}

}