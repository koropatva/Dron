package com.dron.standalone.controllers.root.controls;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class RootConfig {

	private static final double POST_BODY_HEADERS_HEIGHT = 470.0;

	private static final double TBL_HEADERS_HEIGHT = 170.0;

	private static DoubleProperty postBodyHeaders = new SimpleDoubleProperty(
			POST_BODY_HEADERS_HEIGHT);

	private static final DoubleProperty headersHeihgt = new SimpleDoubleProperty(
			TBL_HEADERS_HEIGHT);

	private static DoubleBinding postBodyHeight = postBodyHeaders
			.subtract(headersHeihgt);

	public static void bindHeaders(boolean visible) {
		if (visible) {
			headersHeihgt.set(TBL_HEADERS_HEIGHT);
		} else {
			headersHeihgt.set(0);
		}
	}

	public static double getHeadersHeight() {
		return headersHeihgt.get();
	}

	public static double getPostBodyHeight() {
		return postBodyHeight.get();
	}
}
