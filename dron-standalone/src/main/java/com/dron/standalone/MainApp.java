package com.dron.standalone;

import javafx.application.Application;
import javafx.stage.Stage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dron.standalone.actions.interfaces.IStageService;
import com.dron.standalone.models.ControllerEnum;

public class MainApp extends Application {

	private static final String SPRING_CONTEXT = "/META-INF/spring/context.xml";

	private static ApplicationContext ctx;

	@Override
	public void start(Stage primaryStage) throws Exception {
		IStageService iStageService = ctx.getBean(IStageService.class);
		//Initialize base configurations of PrimaryStage
		iStageService.initPrimaryStage(primaryStage);
		
		//Show ROOT controller
		iStageService.showController(ControllerEnum.ROOT);
	}

	public static void main(String[] args) {
		ctx = new ClassPathXmlApplicationContext(SPRING_CONTEXT);
		launch(args);

	}
}
