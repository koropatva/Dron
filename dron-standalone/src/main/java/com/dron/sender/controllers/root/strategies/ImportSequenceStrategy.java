package com.dron.sender.controllers.root.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.config.AppProperties;
import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.base.interfaces.IStageService;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.AutoFillSequenceHelper;
import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.exim.ImportService;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;
import com.dron.sender.pattern.services.transformers.TransformerFactory;
import com.dron.sender.sequence.models.Sequence;

@Component
public class ImportSequenceStrategy extends ModelRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private IStageService iStageService;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_IMPORT_SEQUENCE;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		final RootController controller = (RootController) iBaseController;
		setUp(controller);

		try {
			if (getTmpImportFile() != null) {
				Sequence sequence = ImportService.getInstance().imports(
						getTmpImportFile(), Sequence.class);
				TransformerFactory.transformEntity(sequence, uiSequence,
						TransformKey.ROOT_SEQUENCE);

				controller.setTmpImportSequence(sequence);

				autoFillSequenceTextBox.setItems(AutoFillSequenceHelper
						.getFiles(getTmpImportFile()));

				context.execute(controller,
						ControllerActionStrategy.ROOT_PREPARE_SEQUENCE);
			}
		} catch (DronSenderException e) {
			System.out.println(e.getMessage());
		}
	}
}
