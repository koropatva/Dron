package com.dron.sender.pattern.services.strategies;

import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.HeaderTableView;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;

@Component
public class FillUIHeadersStrategy extends ModelRootController implements
		IControllerStrategy {

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.FILL_UI_HEADERS;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		final RootController controller = (RootController) iBaseController;
		setUp(controller);

		tblHeaders = new HeaderTableView().initialize(uiSequence.getUiPlugins()
				.get(uiSequence.getSelectedUIPLugin()).getHeadersList(),
				tblHeaders);
	}

}
