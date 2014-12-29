package com.dron.sender;

import javafx.application.Application;
import javafx.stage.Stage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dron.sender.actions.interfaces.IStageService;
import com.dron.sender.config.AppConfiguration;
import com.dron.sender.models.ControllerEnum;

public class MainApp extends Application {

	private ApplicationContext ctx;

	@Override
	public void start(Stage primaryStage) throws Exception {
		ctx = new AnnotationConfigApplicationContext(AppConfiguration.class);

		IStageService iStageService = ctx.getBean(IStageService.class);
		iStageService.initPrimaryStage(primaryStage);

		iStageService.showController(ControllerEnum.ROOT);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
