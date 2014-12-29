package com.dron.sender.actions.interfaces;

import javafx.stage.Stage;

import com.dron.sender.models.ControllerEnum;

public interface IStageService {

	void initPrimaryStage(Stage primaryStage);

	void showController(ControllerEnum controllerEnum);
}
