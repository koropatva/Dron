package com.dron.sender.pattern.services.transformers;

import java.util.List;

import javafx.collections.ObservableList;

import com.dron.sender.controllers.root.models.UIFutureParam;
import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.sequence.models.FutureParam;

public class FutureParamTransformer implements
		IBaseTransformer<List<FutureParam>, ObservableList<UIFutureParam>> {

	@Override
	public ObservableList<UIFutureParam> transform(
			final List<FutureParam> futureParams,
			final ObservableList<UIFutureParam> uiFutureParams) {
		uiFutureParams.clear();
		futureParams.forEach(futureParam -> uiFutureParams
				.add(new UIFutureParam(futureParam)));
		uiFutureParams.add(new UIFutureParam());
		return uiFutureParams;
	}

}
