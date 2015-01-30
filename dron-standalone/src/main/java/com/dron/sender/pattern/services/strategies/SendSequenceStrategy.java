package com.dron.sender.pattern.services.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.tasks.SequenceTask;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.observers.BaseLoggerObserver;
import com.dron.sender.pattern.services.transformers.TransformerFactory;
import com.dron.sender.sequence.models.Param;
import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;

@Component
public class SendSequenceStrategy extends ModelRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.SEND_SEQUENCE;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		RootController controller = (RootController) iBaseController;
		setUp(controller);

		Sequence sequence = fillRootSequence();
		initLogging(sequence);

		txaResponce.setText("");
		context.execute(controller, ControllerActionStrategy.DISABLE_CONTROLS);

		SequenceTask sequenceTask = new SequenceTask(sequence);
		sequenceTask.setContext(context);
		sequenceTask.setController(controller);

		new Thread(sequenceTask).start();
	}

	private Sequence fillRootSequence() {
		Sequence sequence = new Sequence();
		TransformerFactory.reverseTransformEntity(sequence, uiSequence,
				TransformKey.SEQUENCE);
		return sequence;
	}

	private void initLogging(Sequence sequence) {
		for (Plugin plugin : sequence.getPlugins()) {
			new BaseLoggerObserver(plugin, txaResponce,
					Plugin.PROPERTY_RESPONCE);
		}

		for (Param param : sequence.getParams()) {
			new BaseLoggerObserver(param, txaResponce);
		}
	}

}
