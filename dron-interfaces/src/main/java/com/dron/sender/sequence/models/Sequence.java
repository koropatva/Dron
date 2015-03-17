package com.dron.sender.sequence.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Sequence extends BaseNotificationModel {

	public static final String PROPERTY_ID = "ID";

	public static final String PROPERTY_ORDER = "Order";

	public static final String PROPERTY_NAME = "Name";

	public static final String PROPERTY_PARAMS = "Params";

	public static final String PROPERTY_PARAM = "Param";

	private String id;

	private String name;

	private List<String> order;

	private List<Plugin> plugins = new LinkedList<Plugin>();

	private List<Param> params = new ArrayList<Param>();

	public String findParam(String key) {
		for (Param iterable : params) {
			if (iterable.getKey().equals(key)) {
				return iterable.getValue();
			}
		}
		return null;
	}

	public void updateParam(Param param) {
		for (Param iterable : params) {
			if (iterable.getKey().equals(param.getKey())) {
				notifyListeners(this, PROPERTY_PARAM, iterable.getValue(),
						param.getValue());
				iterable.setValue(param.getValue());
				break;
			}
		}
	}

	public void addParam(Param param) {
		notifyListeners(this, PROPERTY_PARAMS, this.params, params.add(param));
	}

	public List<Param> getParams() {
		return params;
	}

	public List<Plugin> getPlugins() {
		return plugins;
	}

	public void setPlugins(List<Plugin> plugins) {
		this.plugins = plugins;
	}

	public void setParams(List<Param> params) {
		notifyListeners(this, PROPERTY_PARAMS, this.params,
				this.params = params);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		notifyListeners(this, PROPERTY_NAME, this.name, this.name = name);
	}

	public List<String> getOrder() {
		return order;
	}

	public void setOrder(List<String> order) {
		notifyListeners(this, PROPERTY_ORDER, this.order, this.order = order);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		notifyListeners(this, PROPERTY_ID, this.id, this.id = id);
	}
}