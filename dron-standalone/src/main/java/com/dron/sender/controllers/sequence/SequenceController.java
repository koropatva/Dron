package com.dron.sender.controllers.sequence;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.base.interfaces.IStageService;
import com.dron.sender.controllers.base.models.ControllerEnum;
import com.dron.sender.controllers.root.RootController;

@Component
public class SequenceController extends Parent implements IBaseController {

	@Autowired
	private IStageService iStageService;

	private FXMLLoader loader;

	public SequenceController() {
		// Get path for class location
		String viewPath = this.getClass().getPackage().getName()
				.replace('.', '/');

		loader = new FXMLLoader(RootController.class.getResource("/" + viewPath
				+ "/" + getControllerEnum().getViewName() + ".fxml"));
		loader.setController(this);
	}

	public FXMLLoader getLoader() {
		return loader;
	}

	@Override
	public ControllerEnum getControllerEnum() {
		return ControllerEnum.SEQUENCE;
	}

	@FXML
	private void swapRootScene(ActionEvent event) {
		iStageService.showController(ControllerEnum.ROOT);
	}

}
