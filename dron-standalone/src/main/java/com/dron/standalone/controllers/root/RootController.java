package com.dron.standalone.controllers.root;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import org.springframework.stereotype.Component;

import com.dron.exceptions.EmptyDataException;
import com.dron.models.Param;
import com.dron.models.Plugin;
import com.dron.models.Sequence;
import com.dron.services.SequenceService;
import com.dron.standalone.controllers.base.BaseController;
import com.dron.standalone.controllers.root.observers.BaseLoggerObserver;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RootController extends BaseController {

	@FXML
	private Button btnSend;

	@FXML
	private TextArea txaLogger;

	@Override
	protected String getViewName() {
		return "RootView";
	}

	@FXML
	protected void send(ActionEvent actionEvent) {
		try {
			Sequence sequence;

			ObjectMapper mapper = new ObjectMapper();

			sequence = mapper
					.readValue(
							new File(
									"/Users/admin/Documents/dron-project/dron/src/main/resources/json/Test.json"),
							Sequence.class);
			initLogging(sequence);

			SequenceService sequenceService = new SequenceService(sequence);
			sequenceService.runSequence();
		} catch (IOException | EmptyDataException e) {
			System.out.println(e.getMessage());
		}
	}

	private void initLogging(Sequence sequence) {
		for (Plugin plugin : sequence.getPlugins()) {
			new BaseLoggerObserver(plugin, txaLogger, Plugin.PROPERTY_RESPONCE);
		}

		for (Param param : sequence.getParams()) {
			new BaseLoggerObserver(param, txaLogger);
		}
	}

}
