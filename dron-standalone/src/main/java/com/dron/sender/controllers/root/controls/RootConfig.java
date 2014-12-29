package com.dron.sender.controllers.root.controls;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TextArea;

import com.dron.sender.ui.models.UIHttpMethod;

public class RootConfig {

	private static final double TBL_HEADERS_HEIGHT = 170.0;

	private static final double TXA_POST_BODY_HEIGHT = 300.0;

	private static final double TXA_RESPONCE_HEIGHT = 100.0;

	private static DoubleProperty headersHeihgt = new SimpleDoubleProperty(
			TBL_HEADERS_HEIGHT);

	private static DoubleBinding headersBinding = new SimpleDoubleProperty(
			TBL_HEADERS_HEIGHT).subtract(headersHeihgt);

	private static DoubleProperty postBodyHeight = new SimpleDoubleProperty(
			TXA_POST_BODY_HEIGHT);

	private static DoubleBinding postBodyBind = new SimpleDoubleProperty(
			TXA_POST_BODY_HEIGHT).subtract(postBodyHeight);

	private static DoubleBinding postBodyBinding = postBodyHeight
			.add(headersBinding);

	private static DoubleProperty responceHeight = new SimpleDoubleProperty(
			TXA_RESPONCE_HEIGHT);

	private static DoubleBinding responceBind = responceHeight.add(
			headersBinding).add(postBodyBind);

	public static void bindHeaders(boolean visible) {
		if (visible) {
			headersHeihgt.set(TBL_HEADERS_HEIGHT);
		} else {
			headersHeihgt.set(-10);
		}
	}

	public static void bindPostBody(TextArea txaPostBody, UIHttpMethod method) {
		switch (method.getMethod()) {
		case POST:
			txaPostBody.setVisible(true);
			postBodyHeight.set(TXA_POST_BODY_HEIGHT);
			break;
		default:
			txaPostBody.setVisible(false);
			postBodyHeight.set(-10);
			break;
		}
	}

	public static double getHeadersHeight() {
		return headersHeihgt.get();
	}

	public static double getPostBodyHeight() {
		return postBodyBinding.get();
	}

	public static double getResponceHeight() {
		return responceBind.get();
	}

}
