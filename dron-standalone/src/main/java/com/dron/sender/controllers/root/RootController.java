package com.dron.sender.controllers.root;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.base.interfaces.IStageService;
import com.dron.sender.controllers.base.models.ControllerEnum;
import com.dron.sender.controllers.root.models.UISequence;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;

@Component
public class RootController extends ModelRootController implements
		Initializable, IBaseController {

	@Autowired
	private IStageService iStageService;

	@Autowired
	private ControllerStrategyContext context;

	private FXMLLoader loader;

	public RootController() {
		// Get path for class location
		String viewPath = this.getClass().getPackage().getName()
				.replace('.', '/');

		loader = new FXMLLoader(RootController.class.getResource("/" + viewPath
				+ "/" + getControllerEnum().getViewName() + ".fxml"));
		loader.setController(this);
	}

	@Override
	public FXMLLoader getLoader() {
		return loader;
	}

	@Override
	public ControllerEnum getControllerEnum() {
		return ControllerEnum.ROOT;
	}

	@Override
	public void initialize(final URL url, final ResourceBundle resource) {
		uiSequence = new UISequence();
		context.execute(this, ControllerActionStrategy.ROOT_INITIALIZE);
	}

	@FXML
	protected void createNewSequence() {
		context.execute(this, ControllerActionStrategy.ROOT_NEW_UI_SEQUENCE);
	}

	@FXML
	protected void addNewPlugin(final ActionEvent event) {
		context.execute(this, ControllerActionStrategy.ROOT_NEW_UI_PLUGIN);
	}

	@FXML
	protected void exportSequence() {
		context.execute(this, ControllerActionStrategy.ROOT_EXPORT_SEQUENCE);
	}

	@FXML
	protected void importSequence() {
		context.execute(this,
				ControllerActionStrategy.ROOT_SHOW_IMPORT_DIALOG_SEQUENCE);
	}

	@FXML
	protected void sendSequence(final ActionEvent actionEvent) {
		context.execute(this, ControllerActionStrategy.ROOT_SEND_SEQUENCE);
	}

	@FXML
	protected void sendActivePlugin(final ActionEvent actionEvent) {
		context.execute(this, ControllerActionStrategy.ROOT_SEND_ACTIVE_PLUGIN);
	}
}
