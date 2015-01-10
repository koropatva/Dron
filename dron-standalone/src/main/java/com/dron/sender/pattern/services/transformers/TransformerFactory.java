package com.dron.sender.pattern.services.transformers;

import org.springframework.util.Assert;

import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.pattern.models.transformers.TransformKey;

public class TransformerFactory {

	public static <F, T> T transformEntity(F from, Class<T> toClazz,
			TransformKey key) {
		try {
			T to = toClazz.newInstance();
			return transformEntity(from, to, key);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static <T, F> T transformEntity(F from, T to, TransformKey key) {
		Assert.notNull(from,
				"Transfromation is wrong as transformed from object is null.");
		Assert.notNull(to,
				"Transformation is wrong as transformed to object is null.");
		IBaseTransformer<F, T> transformer = getTransformer(from, to, key);
		return transformer.transform(from, to);
	}

	@SuppressWarnings({ "unchecked" })
	private static <F, T> IBaseTransformer<F, T> getTransformer(F from, T to,
			TransformKey key) {
		switch (key) {
		case HTTP_HEADERS_INTO_UI_HEADERS:
			return (IBaseTransformer<F, T>) new HttpHeaderTransformer();
		case PARAMS_INTO_UI_PARAMS:
			return (IBaseTransformer<F, T>) new ParamTransformer();
		case FUTURE_PARAMS_INTO_UI_FUTURE_PARAMS:
			return (IBaseTransformer<F, T>) new FutureParamTransformer();
		case FILL_PLUGIN_FUTURE_PARAMS:
			return (IBaseTransformer<F, T>) new FillPluginFutureParamsTransformer();
		case FILL_TITLED_PANES_WITH_FUTURE_PARAMS:
			return (IBaseTransformer<F, T>) new FillTitledPanesWithFutureParamsTransformer();
		default:
			return null;
		}
	}
}
