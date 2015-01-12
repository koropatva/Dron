package com.dron.sender.controllers.root.controls;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TextArea;

public class RootConfig {

	private static final double HB_HEADERS_PARAMS_HEIGHT = 170.0;

	private static final double TXA_POST_BODY_HEIGHT = 300.0;

	private static final double TXA_RESPONCE_HEIGHT = 100.0;

	private static DoubleProperty hbHeadersParamsHeihgt = new SimpleDoubleProperty(
			HB_HEADERS_PARAMS_HEIGHT);

	private static DoubleBinding hbHeadersParamsBinding = new SimpleDoubleProperty(
			HB_HEADERS_PARAMS_HEIGHT).subtract(hbHeadersParamsHeihgt);

	private static DoubleProperty postBodyHeight = new SimpleDoubleProperty(
			TXA_POST_BODY_HEIGHT);

	private static DoubleBinding postBodyBind = new SimpleDoubleProperty(
			TXA_POST_BODY_HEIGHT).subtract(postBodyHeight);

	private static DoubleBinding postBodyBinding = postBodyHeight
			.add(hbHeadersParamsBinding);

	private static DoubleProperty responceHeight = new SimpleDoubleProperty(
			TXA_RESPONCE_HEIGHT);

	private static DoubleBinding responceBind = responceHeight.add(
			hbHeadersParamsBinding).add(postBodyBind);

	public static void bindHeaders(boolean visible) {
		if (visible) {
			hbHeadersParamsHeihgt.set(HB_HEADERS_PARAMS_HEIGHT);
		} else {
			hbHeadersParamsHeihgt.set(-10);
		}
	}

	public static void bindPostBody(TextArea txaPostBody, String method) {
		switch (method) {
		case "POST":
			txaPostBody.setVisible(true);
			postBodyHeight.set(TXA_POST_BODY_HEIGHT);
			break;
		default:
			txaPostBody.setVisible(false);
			postBodyHeight.set(-10);
			break;
		}
	}

	public static double getHbHeadersParamsHeight() {
		return hbHeadersParamsHeihgt.get();
	}

	public static double getPostBodyHeight() {
		return postBodyBinding.get();
	}

	public static double getResponceHeight() {
		return responceBind.get();
	}

}
