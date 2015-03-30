package com.dron.sender.controllers.root.tasks;

import org.springframework.context.ApplicationContext;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.util.Duration;

import com.dron.sender.config.AppProperties;
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

	private AppProperties appProperties;

	public SequenceTask(Sequence sequence, ApplicationContext ctx) {
		this.sequence = sequence;
		this.appProperties = ctx.getBean(AppProperties.class);
		this.controller = ctx.getBean(RootController.class);
		this.context = ctx.getBean(ControllerStrategyContext.class);
	}

	@Override
	protected String call() {
		final SequenceService sequenceService = new SequenceService(sequence);

		Timeline timeline = new Timeline(new KeyFrame(
				Duration.minutes(appProperties.getRequestDuration()), (ae) -> {
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

		return null;
	}
}
