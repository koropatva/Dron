package com.dron.sender.controllers.root.tasks;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.util.Duration;

import com.dron.sender.controllers.root.RootController;
import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.exceptions.HandlerNotReadyException;
import com.dron.sender.exceptions.RequestException;
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
		final SequenceService sequenceService = new SequenceService(sequence);

		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5),
				(ae) -> {
					try {
						sequenceService.runSequence();
					} catch (DronSenderException e) {
						if (e instanceof HandlerNotReadyException) {
							System.out.println("Dron ERROR " + e.getMessage());
						}
						if (e instanceof RequestException) {
							System.out.println("Request ERROR "
									+ e.getMessage());
						}
					} catch (Exception e) {
						System.out.println("System ERROR " + e.getMessage());
					} finally {
						context.execute(controller,
								ControllerActionStrategy.ROOT_ENABLE_CONTROLS);
					}
				}));
		timeline.play();

		// Platform.runLater(new Runnable() {
		// public void run() {
		// context.execute(controller,
		// ControllerActionStrategy.ROOT_ENABLE_CONTROLS);
		// }
		// });

		return null;
	}
}
