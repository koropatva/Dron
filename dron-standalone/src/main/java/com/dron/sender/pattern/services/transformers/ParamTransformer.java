package com.dron.sender.pattern.services.transformers;

import java.util.List;

import javafx.collections.ObservableList;

import com.dron.sender.controllers.root.models.UIParam;
import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.sequence.models.Param;

public class ParamTransformer implements
		IBaseTransformer<List<Param>, ObservableList<UIParam>> {

	@Override
	public ObservableList<UIParam> transform(final List<Param> params,
			final ObservableList<UIParam> uiParams) {
		uiParams.clear();
		params.forEach(param -> {
			uiParams.add(new UIParam(param));
		});
		uiParams.add(new UIParam());
		return uiParams;
	}

}
