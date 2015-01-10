package com.dron.sender.controllers.sequence;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.BaseController;
import com.dron.sender.controllers.base.interfaces.IStageService;
import com.dron.sender.controllers.base.models.ControllerEnum;

@Component
public class SequenceController extends BaseController {

	@Autowired
	private IStageService iStageService;

	@Override
	protected ControllerEnum getControllerEnum() {
		return ControllerEnum.SEQUENCE;
	}

	@FXML
	private void swapRootScene(ActionEvent event) {
		iStageService.showController(ControllerEnum.ROOT);
	}

}
