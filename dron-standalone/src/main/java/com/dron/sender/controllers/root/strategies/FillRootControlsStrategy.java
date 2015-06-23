package com.dron.sender.controllers.root.strategies;

import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.HeaderTableView;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.models.BaseRootController;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;

@Component
public class FillRootControlsStrategy extends BaseRootController implements
		IControllerStrategy {

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_FILL_ROOT_CONTROLS;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		final RootController controller = (RootController) iBaseController;
		setUp(controller);

		tfUrl.setText(controller.getUiPlugin().getUrl().get());

		cbMethods.getSelectionModel().select(
				controller.getUiPlugin().getMethod().get());

		txaPostBody.setText(controller.getUiPlugin().getPostBody().get());

		RootConfig.bindPostBody(txaPostBody, controller.getUiPlugin()
				.getMethod().get());

		tblHeaders = new HeaderTableView().initialize(controller.getUiPlugin()
				.getHeadersList(), tblHeaders, null);
	}
}
