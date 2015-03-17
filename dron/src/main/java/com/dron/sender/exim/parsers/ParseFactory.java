package com.dron.sender.exim.parsers;

import java.nio.file.Path;

import com.dron.sender.sequence.models.Sequence;


public class ParseFactory {

	public static ParseFactory INSTANCE;

	private ParseFactory() {
	}

	public static ParseFactory getInstance() {
		if (INSTANCE == null) {
			synchronized (ParseFactory.class) {
				if (INSTANCE == null) {
					INSTANCE = new ParseFactory();
				}
			}
		}
		return INSTANCE;
	}

	public Sequence parse(Path path, ParserType type){
		switch (type) {
			case POSTMAN:
				
				break;

			default:
				break;
		}
		return null;
	}
	
}
