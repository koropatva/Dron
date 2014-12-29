package com.dron.sender.controllers.root.tasks;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import com.dron.sender.exceptions.EmptyDataException;
import com.dron.sender.sequence.models.Sequence;
import com.dron.sender.sequence.services.SequenceService;

public class SequenceTask extends Service<Void> {

	private Sequence sequence;

	public SequenceTask(Sequence sequence) {
		this.sequence = sequence;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					SequenceService sequenceService = new SequenceService(
							sequence);
					sequenceService.runSequence();
				} catch (EmptyDataException e) {
					System.out.println(e.getMessage());
				}
				return null;
			}
		};
	}

}
