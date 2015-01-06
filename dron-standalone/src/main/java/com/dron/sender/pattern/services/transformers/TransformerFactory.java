package com.dron.sender.pattern.services.transformers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;

import com.dron.sender.pattern.interfaces.IBaseTransformer;

public class TransformerFactory {

	public static <F, T> T transformEntity(F from, Class<T> toClazz) {
		try {
			T to = toClazz.newInstance();
			return transformEntity(from, to);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static <T, F> T transformEntity(F from, T to) {
		Assert.notNull(from,
				"Transfromation is wrong as transformed from object is null.");
		Assert.notNull(to,
				"Transformation is wrong as transformed to object is null.");
		IBaseTransformer<F, T> transformer = getTransformer(from, to);
		return transformer.transform(from, to);
	}

	@SuppressWarnings({ "unchecked" })
	private static <F, T> IBaseTransformer<F, T> getTransformer(F from, T to) {
		// HttpHeaders can be transformed just into one List<UiHttpHeaders>
		if (from instanceof HttpHeaders && to instanceof List<?>) {
			return (IBaseTransformer<F, T>) new HttpHeaderTransformer();
		}
		return null;
	}
}
