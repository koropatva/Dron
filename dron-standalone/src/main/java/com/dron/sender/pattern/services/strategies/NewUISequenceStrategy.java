package com.dron.sender.pattern.services.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.models.UIFutureParam;
import com.dron.sender.controllers.root.models.UIHttpHeaders;
import com.dron.sender.controllers.root.models.UIParam;
import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;

@Component
public class NewUISequenceStrategy extends ModelRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.NEW_UI_SEQUENCE;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		RootController controller = (RootController) iBaseController;
		setUp(controller);
		// Clear all data
		uiSequence.clear();
		uiSequence.getUIParams().add(new UIParam());
		uiSequence.getUiPlugins().add(new UIPlugin());

		rootUiPlugin.clear();
		rootUiPlugin.getFutureParams().add(new UIFutureParam());
		rootUiPlugin.getHeadersList().add(new UIHttpHeaders());

		context.execute(iBaseController,
				ControllerActionStrategy.FILL_UI_PLUGIN_ACCORDION);
	}

}
