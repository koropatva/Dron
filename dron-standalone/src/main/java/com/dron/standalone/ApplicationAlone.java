package com.dron.standalone;

import javafx.application.Application;
import javafx.stage.Stage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dron.frontend.interfaces.IInitControlService;

public class ApplicationAlone extends Application {

    private static ApplicationContext ctx;

    @Override
    public void start(Stage primaryStage) throws Exception {
        IInitControlService initControlService = ctx.getBean(IInitControlService.class);
        initControlService.init(primaryStage);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }


    public static void main(String[] args) {
        ctx = new ClassPathXmlApplicationContext("META-INF/spring/context.xml");
        launch(args);

    }
}
