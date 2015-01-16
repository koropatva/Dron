package com.dron.sender.pattern.services.strategies;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.transformers.TransformerFactory;
import com.dron.sender.sequence.models.Sequence;
import com.fasterxml.jackson.databind.ObjectMapper;

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

		readSequence("/Users/admin/Documents/dron-project/dron/src/main/resources/json/"
				+ tfSequenceName.getText() + ".json");
	}

	private void readSequence(String path) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Sequence sequence = mapper
					.readValue(new File(path), Sequence.class);

			fillUiSequence(sequence);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private void fillUiSequence(Sequence sequence) {
		getUiSequence().clear();
		getRootUiPlugin().clear();

		TransformerFactory.transformEntity(sequence, getUiSequence(),
				TransformKey.SEQUENCE);

		TransformerFactory.transformEntity(
				getUiSequence().getUiPlugins().get(DEFAULT_SELECTED_UI_PLUGIN),
				getRootUiPlugin(), TransformKey.ROOT_UI_PLUGIN);

		context.execute(controller,
				ControllerActionStrategy.FILL_UI_PLUGIN_ACCORDION);
	}

}
