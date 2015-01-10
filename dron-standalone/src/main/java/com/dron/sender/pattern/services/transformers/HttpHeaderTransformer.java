package com.dron.sender.pattern.services.transformers;

import java.util.List;

import org.springframework.http.HttpHeaders;

import com.dron.sender.controllers.root.models.UIHttpHeaders;
import com.dron.sender.pattern.interfaces.IBaseTransformer;

public class HttpHeaderTransformer implements
		IBaseTransformer<HttpHeaders, List<UIHttpHeaders>> {

	@Override
	public List<UIHttpHeaders> transform(final HttpHeaders headers,
			final List<UIHttpHeaders> headersList) {
		headersList.clear();
		headers.forEach((key, value) -> {
			headersList.add(new UIHttpHeaders(key, value.get(0)));
		});
		headersList.add(new UIHttpHeaders());
		return headersList;
	}

}
