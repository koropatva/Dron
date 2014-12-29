package com.dron.sender.actions.interfaces;

import javafx.scene.Scene;

import com.dron.sender.models.ControllerEnum;

public interface ISceneService {
	Scene findScene(ControllerEnum controllerEnum);
}
