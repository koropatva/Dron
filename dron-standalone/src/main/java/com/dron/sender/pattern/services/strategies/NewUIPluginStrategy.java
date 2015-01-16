package com.dron.sender.pattern.services.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;

@Component
public class NewUIPluginStrategy extends ModelRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.NEW_UI_PLUGIN;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		RootController controller = (RootController) iBaseController;
		setUp(controller);

		UIPlugin uiPlugin = new UIPlugin();
		uiPlugin.setName(tfNewPluginName.getText());
		uiSequence.getUiPlugins().add(uiPlugin);

		tfNewPluginName.setText("");

		context.execute(controller,
				ControllerActionStrategy.FILL_UI_PLUGIN_ACCORDION);

		accPlugins.setExpandedPane(accPlugins.getPanes().get(
				accPlugins.getPanes().size() - 1));
	}

}
