package com.dron.standalone.controllers.sequence;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.standalone.actions.interfaces.IStageService;
import com.dron.standalone.controllers.base.BaseController;
import com.dron.standalone.models.ControllerEnum;

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
