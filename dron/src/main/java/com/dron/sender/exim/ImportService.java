package com.dron.sender.exim;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.sequence.models.Sequence;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service for exporting and importing sequences between dron and file system
 * 
 * @author Koropatva
 *
 */
public class ImportService {

	public static ImportService INSTANCE;

	private ObjectMapper mapper = new ObjectMapper();

	private ImportService() {
	}

	public static ImportService getInstance() {
		if (INSTANCE == null) {
			synchronized (ImportService.class) {
				if (INSTANCE == null) {
					INSTANCE = new ImportService();
				}
			}
		}
		return INSTANCE;
	}

	/**
	 * Method for import sequence from the file
	 * 
	 * @param path
	 *            path for the file to import
	 * @return a sequence from the file
	 * @throws DronSenderException
	 */
	public Sequence imports(File file) throws DronSenderException {
		return imports(file.toURI());
	}

	/**
	 * Method for import sequence from the path
	 * 
	 * @param path
	 *            path for the file to import
	 * @return a sequence from the file
	 * @throws DronSenderException
	 */
	public Sequence imports(String path) throws DronSenderException {
		return imports(Paths.get(URI.create(path)));
	}
	
	/**
	 * Method for import sequence from the URI
	 * 
	 * @param path
	 *            path for the file to import
	 * @return a sequence from the file
	 * @throws DronSenderException
	 */
	public Sequence imports(URI uri) throws DronSenderException {
		return imports(Paths.get(uri));
	}

	/**
	 * Method for import sequence from the path
	 * 
	 * @param path
	 *            path for the file to import
	 * @return a sequence from the file
	 * @throws DronSenderException
	 */
	public Sequence imports(Path path) throws DronSenderException {
		try {
			return imports(Files.newInputStream(path, StandardOpenOption.READ));
		} catch (IOException e) {
			throw new DronSenderException(e.getMessage());
		}
	}

	/**
	 * Method for import sequence from the InputStream
	 * 
	 * @param file
	 *            file to import
	 * @return a sequence from the file
	 * @throws DronSenderException
	 */
	public Sequence imports(InputStream src) throws DronSenderException {
		try {
			Sequence sequence = mapper.readValue(src, Sequence.class);
			return sequence;
		} catch (IOException e) {
			throw new DronSenderException(e.getMessage(), e);
		}
	}

}