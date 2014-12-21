package com.dron.standalone.actions.interfaces;

import javafx.stage.Stage;

import com.dron.standalone.models.ControllerEnum;

public interface IStageService {

	void initPrimaryStage(Stage primaryStage);

	void showController(ControllerEnum controllerEnum);
}
