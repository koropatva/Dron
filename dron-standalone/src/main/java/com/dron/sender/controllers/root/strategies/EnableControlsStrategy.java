package com.dron.sender.controllers.root.strategies;

import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;

@Component
public class EnableControlsStrategy extends ModelRootController implements
		IControllerStrategy {

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_ENABLE_CONTROLS;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		final RootController controller = (RootController) iBaseController;
		setUp(controller);

		RootConfig.setDisableRootProperty(false);
		tblParams.disableProperty().set(false);
		tbtnParams.disableProperty().set(false);
		accPlugins.disableProperty().set(false);
		tfNewPluginName.disableProperty().set(false);
		btnAddNewPlugin.disableProperty().set(false);
		btnSendSequence.requestFocus();
	}
}
