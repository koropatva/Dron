package com.dron.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;

public class RequestScene {

	@FXML
	protected void onSelectCB(Event event) {
		System.out.println(event.toString());
	}
}
