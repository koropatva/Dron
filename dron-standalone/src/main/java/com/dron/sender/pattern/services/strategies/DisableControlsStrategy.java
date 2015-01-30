package com.dron.sender.pattern.services.strategies;

import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;

@Component
public class DisableControlsStrategy extends ModelRootController implements
		IControllerStrategy {

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.DISABLE_CONTROLS;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		RootController controller = (RootController) iBaseController;
		setUp(controller);

		RootConfig.setDisableRootProperty(true);
		tblParams.disableProperty().set(true);
		tbtnParams.disableProperty().set(true);
		accPlugins.disableProperty().set(true);
		tfNewPluginName.disableProperty().set(true);
		btnAddNewPlugin.disableProperty().set(true);
	}
}
