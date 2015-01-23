package com.dron.sender.pattern.services.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.transformers.TransformerFactory;

@Component
public class PrepareSequenceStrategy extends ModelRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	private RootController controller;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.PREPARE_SEQUENCE;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		controller = (RootController) iBaseController;
		setUp(controller);

		uiSequence.clear();

		TransformerFactory.transformEntity(getTmpImportSequence(), uiSequence,
				TransformKey.SEQUENCE);

		context.execute(controller,
				ControllerActionStrategy.FILL_UI_PLUGIN_ACCORDION);

		uiSequence.selectedUIPLugin(0, context, controller);
	}

}
