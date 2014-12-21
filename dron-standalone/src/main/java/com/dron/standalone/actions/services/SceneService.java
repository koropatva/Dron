package com.dron.standalone.actions.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dron.standalone.actions.interfaces.ISceneService;
import com.dron.standalone.controllers.base.interfaces.IBaseController;
import com.dron.standalone.models.ControllerEnum;

/**
 * Service works with scene. It can find scene or create new, if it in not
 * present.
 * 
 * @author admin
 *
 */
@Component
public class SceneService implements ISceneService {

	@Resource
	private ApplicationContext ctx;

	private Map<ControllerEnum, Scene> scenes;

	@PostConstruct
	private void setUp() {
		scenes = new HashMap<>();
	}

	@Override
	public Scene findScene(ControllerEnum controllerEnum) {
		Scene scene = scenes.get(controllerEnum);
		if (scene == null) {
			try {
				IBaseController baseController = (IBaseController) ctx
						.getBean(controllerEnum.getControllerName());
				scene = new Scene((Parent) baseController.getLoader().load());

				// Add new scene to the list
				scenes.put(controllerEnum, scene);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return scene;
	}
}
