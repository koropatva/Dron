package com.dron.sender.pattern.services.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
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
		uiSequence.prepareEmptySequence();

		context.execute(controller,
				ControllerActionStrategy.FILL_UI_PLUGIN_ACCORDION);
		uiSequence.selectedUIPLugin(0, context, controller);
	}

}
