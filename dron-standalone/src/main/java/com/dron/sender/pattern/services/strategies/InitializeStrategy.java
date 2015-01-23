package com.dron.sender.pattern.services.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.ParamTableView;
import com.dron.sender.controllers.root.controls.RootConfig;
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

		uiSequence.clear();
		uiSequence.prepareEmptySequence();

		tfNewPluginName.textProperty().bindBidirectional(uiSequence.getName());

		btnSend.disableProperty().bind(RootConfig.getDisableRootProperty());
		
		tfUrl.disableProperty().bind(RootConfig.getDisableRootProperty());
		tfUrl.textProperty().addListener((observer, oldValue, newValue) -> {
			uiSequence.getSelectedUIPLugin().setUrl(newValue);
		});

		cbMethods.disableProperty().bind(RootConfig.getDisableRootProperty());
		cbMethods.getItems().addAll(HttpMethod.GET.name(),
				HttpMethod.POST.name(), HttpMethod.PUT.name(),
				HttpMethod.DELETE.name());
		cbMethods.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					cbMethods.getSelectionModel().select(newValue);
					uiSequence.getSelectedUIPLugin().setMethod(newValue);

					RootConfig.bindPostBody(txaPostBody, newValue);

					updateControls();
				});

		tblParams = new ParamTableView().initialize(uiSequence.getUIParams(),
				tblParams);

		txaPostBody.managedProperty().bind(txaPostBody.visibleProperty());
		txaPostBody.disableProperty().bind(RootConfig.getDisableRootProperty());
		txaPostBody.textProperty().addListener(
				(observer, oldValue, newValue) -> {
					uiSequence.getSelectedUIPLugin().setPostBody(newValue);
				});

		tbtnHeaders.disableProperty().bind(RootConfig.getDisableRootProperty());
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

		tblHeaders.disableProperty().bind(RootConfig.getDisableRootProperty());

		context.execute(controller, ControllerActionStrategy.NEW_UI_SEQUENCE);

		context.execute(controller, ControllerActionStrategy.FILL_ROOT_CONTROLS);
	}
}
