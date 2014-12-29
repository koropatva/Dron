package com.dron.sender.pattern.services.transformers;

import java.util.List;

import org.springframework.http.HttpHeaders;

import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.ui.models.UIHttpHeaders;

public class HttpHeaderTransformer implements IBaseTransformer<HttpHeaders, List<UIHttpHeaders>>{

	@Override
	public List<UIHttpHeaders> transform(HttpHeaders arg0,
			List<UIHttpHeaders> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
