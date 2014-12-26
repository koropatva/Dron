package com.dron.standalone.controllers.root;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.standalone.actions.interfaces.IStageService;
import com.dron.standalone.controllers.base.BaseController;
import com.dron.standalone.controllers.root.tasks.SequenceTask;
import com.dron.standalone.models.ControllerEnum;

@Component
public class RootController extends BaseController {

	@Autowired
	private IStageService iStageService;

	@FXML
	private Button btnSend;

	@FXML
	private TextArea txaLogger;

	@Override
	protected ControllerEnum getControllerEnum() {
		return ControllerEnum.ROOT;
	}

	@FXML
	protected void send(ActionEvent actionEvent) {
		SequenceTask sequenceTask = new SequenceTask(txaLogger,
				"/Users/admin/Documents/dron-project/dron/src/main/resources/json/Test.json");

		sequenceTask.start();
	}

	@FXML
	private void swatSequenceScene(ActionEvent event) {
		iStageService.showController(ControllerEnum.SEQUENCE);
	}

}
