package com.dron.standalone.controllers.root;

import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import com.dron.standalone.controllers.base.BaseController;

@Component
public class RootController extends BaseController {

	@Override
	protected String getViewName() {
		return "RootView";
	}

	@FXML
	private Button label;

	private int count = 0;

	@FXML
	protected void doSomething(ActionEvent actionEvent) {
		label.setText("Clicked!!" + count++);
	}

}
