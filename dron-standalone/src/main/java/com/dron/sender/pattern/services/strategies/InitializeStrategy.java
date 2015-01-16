package com.dron.sender.pattern.services.strategies;

import javafx.beans.value.ObservableValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.HeaderTableView;
import com.dron.sender.controllers.root.controls.ParamTableView;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.models.UIParam;
import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;

@Component
public class InitializeStrategy extends ModelRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.INITIALIZE;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		RootController controller = (RootController) iBaseController;
		setUp(controller);

		tfNewPluginName.textProperty().bindBidirectional(uiSequence.getName());
		uiSequence.getUIParams().add(new UIParam());
		uiSequence.getUiPlugins().add(new UIPlugin());

		tfUrl.textProperty().bindBidirectional(rootUiPlugin.getUrl());

		txaPostBody.textProperty()
				.bindBidirectional(rootUiPlugin.getPostBody());

		cbMethods
				.getSelectionModel()
				.selectedItemProperty()
				.addListener(
						(ObservableValue<? extends String> observable,
								String oldValue, String newValue) -> {
							if (newValue != null) {
								rootUiPlugin.setMethod(newValue);
							}
						});
		cbMethods.getSelectionModel().select(rootUiPlugin.getMethod().get());

		context.execute(controller, ControllerActionStrategy.NEW_UI_SEQUENCE);

		tblHeaders = new HeaderTableView().initialize(
				rootUiPlugin.getHeadersList(), tblHeaders);

		tblParams = new ParamTableView().initialize(uiSequence.getUIParams(),
				tblParams);

		txaPostBody.managedProperty().bind(txaPostBody.visibleProperty());
		txaPostBody.setEditable(true);

		cbMethods.getItems().addAll(HttpMethod.GET.name(),
				HttpMethod.POST.name(), HttpMethod.PUT.name(),
				HttpMethod.DELETE.name());
		cbMethods.getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldChoice, newChoise) -> {
					cbMethods.getSelectionModel().select(newChoise);
					RootConfig.bindPostBody(txaPostBody, newChoise);

					updateControls();
				});
		cbMethods.getSelectionModel().select(DEFAULT_SELECTED_HTTP_METHOD);

		tbtnHeaders.setOnAction(event -> {
			tblHeaders.setVisible(!tblHeaders.isVisible());
			RootConfig.bindHeaders(tblHeaders.isVisible()
					|| tblParams.isVisible());

			updateControls();
		});

		tbtnParams.setOnAction(event -> {
			tblParams.setVisible(!tblParams.isVisible());
			RootConfig.bindHeaders(tblHeaders.isVisible()
					|| tblParams.isVisible());

			updateControls();
		});

		updateControls();
	}

	private void updateControls() {
		hbHeadersParamsTable.setPrefHeight(RootConfig
				.getHbHeadersParamsHeight());
		txaPostBody.setPrefHeight(RootConfig.getPostBodyHeight());
		txaResponce.setPrefHeight(RootConfig.getResponceHeight());
	}

}
