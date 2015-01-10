package com.dron.sender.pattern.services.transformers;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import com.dron.sender.controllers.root.controls.FutureParamTableView;
import com.dron.sender.controllers.root.models.UIFutureParam;
import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.sequence.models.Plugin;

public class FillTitledPanesWithFutureParamsTransformer
		implements
		IBaseTransformer<Map<Plugin, ObservableList<UIFutureParam>>, List<TitledPane>> {

	private static final double FUTURE_PARAM_TBL_PREF_WIDTH = 320.0;

	@Override
	public List<TitledPane> transform(
			final Map<Plugin, ObservableList<UIFutureParam>> mapFutureParams,
			final List<TitledPane> titledPanes) {
		mapFutureParams.forEach((plugin, futureParams) -> {
			AnchorPane anchorPane = new AnchorPane();
			TableView<UIFutureParam> tblFutureParams = new TableView<>();
			tblFutureParams.setPrefWidth(FUTURE_PARAM_TBL_PREF_WIDTH);
			tblFutureParams = new FutureParamTableView().initialize(
					futureParams, tblFutureParams);

			anchorPane.getChildren().addAll(tblFutureParams);
			TitledPane pluginTitle = new TitledPane(plugin.getName(),
					anchorPane);
			titledPanes.add(pluginTitle);
		});
		return titledPanes;
	}

}
