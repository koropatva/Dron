package com.dron.standalone.controllers.root;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import com.dron.exceptions.EmptyDataException;
import com.dron.models.Sequence;
import com.dron.services.SequenceService;
import com.dron.standalone.controllers.base.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RootController extends BaseController {

	@Override
	protected String getViewName() {
		return "RootView";
	}

	@FXML
	private Button tbnSend;

	@FXML
	private TextArea txaPostData;

	private int count = 0;

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

			SequenceService sequenceService = new SequenceService(sequence);

			sequenceService.runSequence();
		} catch (IOException | EmptyDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
