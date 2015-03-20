package com.dron.sender.exim.parsers;

import java.net.URI;

import org.springframework.stereotype.Service;

import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.exim.parsers.postman.PostmanManager;
import com.dron.sender.sequence.models.Sequence;

@Service
public class ImportFactory {

	public static ImportFactory INSTANCE;

	private ImportFactory() {
	}

	public static ImportFactory getInstance() {
		if (INSTANCE == null) {
			synchronized (ImportFactory.class) {
				if (INSTANCE == null) {
					INSTANCE = new ImportFactory();
				}
			}
		}
		return INSTANCE;
	}

	private PostmanManager postmanManager = PostmanManager.getInstance();

	public Sequence importSequence(URI uri, ParserType type)
			throws DronSenderException {
		Sequence sequence = new Sequence();
		return importSequence(uri, sequence, type);
	}

	public Sequence importSequence(URI uri, Sequence sequence, ParserType type)
			throws DronSenderException {
		switch (type) {
			case POSTMAN:
				postmanManager.parse(uri, sequence);
				break;

			default:
				throw new DronSenderException(String.format(
						"%s for now is not implemented.", type.name()));
		}
		return sequence;
	}
}
