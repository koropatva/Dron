package com.dron.sender.controllers.root.tasks;

import javafx.application.Platform;
import javafx.concurrent.Task;

import com.dron.sender.controllers.root.RootController;
import com.dron.sender.exceptions.EmptyDataException;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;
import com.dron.sender.sequence.models.Sequence;
import com.dron.sender.sequence.services.SequenceService;

public class SequenceTask extends Task<String> {

	private Sequence sequence;

	private ControllerStrategyContext context;

	private RootController controller;

	public final void setContext(ControllerStrategyContext context) {
		this.context = context;
	}

	public final void setController(RootController controller) {
		this.controller = controller;
	}

	public SequenceTask(Sequence sequence) {
		this.sequence = sequence;
	}

	@Override
	protected String call() {
		try {
			SequenceService sequenceService = new SequenceService(sequence);
			sequenceService.runSequence();
			Platform.runLater(new Runnable() {
				public void run() {
					context.execute(controller,
							ControllerActionStrategy.ROOT_ENABLE_CONTROLS);
				}
			});
		} catch (EmptyDataException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
