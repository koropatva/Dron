package com.dron.standalone.actions.interfaces;

import javafx.scene.Scene;

import com.dron.standalone.models.ControllerEnum;

public interface ISceneService {
	Scene findScene(ControllerEnum controllerEnum);
}
