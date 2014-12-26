package com.dron.standalone.controllers.root.tasks;

import java.io.File;
import java.io.IOException;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import com.dron.exceptions.EmptyDataException;
import com.dron.models.Param;
import com.dron.models.Plugin;
import com.dron.models.Sequence;
import com.dron.services.SequenceService;
import com.dron.standalone.controllers.root.observers.BaseLoggerObserver;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SequenceTask extends Service<Void> {

	private String pathFile;

	private TextArea txaLogger;

	public SequenceTask(TextArea txaLogger, String pathFile) {
		this.txaLogger = txaLogger;
		this.pathFile = pathFile;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Sequence sequence;

					ObjectMapper mapper = new ObjectMapper();

					sequence = mapper.readValue(new File(pathFile),
							Sequence.class);
					initLogging(sequence);

					SequenceService sequenceService = new SequenceService(
							sequence);
					sequenceService.runSequence();
				} catch (IOException | EmptyDataException e) {
					System.out.println(e.getMessage());
				}
				return null;
			}
		};
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
