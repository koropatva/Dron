package com.dron.sender.pattern.services.transformers;

import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.dron.sender.controllers.root.models.UIFutureParam;
import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.sequence.models.Plugin;

public class FillPluginFutureParamsTransformer
		implements
		IBaseTransformer<List<Plugin>, Map<Plugin, ObservableList<UIFutureParam>>> {

	@Override
	public Map<Plugin, ObservableList<UIFutureParam>> transform(
			final List<Plugin> plugins,
			final Map<Plugin, ObservableList<UIFutureParam>> mapFutureParams) {
		mapFutureParams.clear();
		plugins.forEach(plugin -> {
			ObservableList<UIFutureParam> futureParams = FXCollections
					.observableArrayList();

			TransformerFactory.transformEntity(plugin.getFutureParams(),
					futureParams,
					TransformKey.FUTURE_PARAMS);

			mapFutureParams.put(plugin, futureParams);
		});
		return mapFutureParams;
	}

}
