package com.dron.sender.exim;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.sequence.models.Sequence;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service for exporting and importing sequences between dron and file system
 * 
 * @author Koropatva
 *
 */
public class ExImService {

	public static ExImService INSTANCE;

	private ObjectMapper mapper = new ObjectMapper();

	private ExImService() {
	}

	public static ExImService getInstance() {
		if (INSTANCE == null) {
			synchronized (ExImService.class) {
				if (INSTANCE == null) {
					INSTANCE = new ExImService();
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
	public Sequence imports(String path) throws DronSenderException {
		File file = getFile(path);
		return imports(file);
	}

	/**
	 * Method for import sequence from the file
	 * 
	 * @param file
	 *            file to import
	 * @return a sequence from the file
	 * @throws DronSenderException
	 */
	public Sequence imports(File file) throws DronSenderException {
		try {
			Sequence sequence = mapper.readValue(file, Sequence.class);
			return sequence;
		} catch (IOException e) {
			throw new DronSenderException(e.getMessage(), e);
		}
	}

	/**
	 * Method for save sequence into the file
	 * 
	 * @param sequence
	 *            sequence for export(save)
	 * @param path
	 *            path for the export file
	 * @throws DronSenderException
	 */
	public void exports(Sequence sequence, String path)
			throws DronSenderException {
		try {
			File file = getFile(path);
			exports(sequence, file);
		} catch (DronSenderException e) {
			throw new DronSenderException(e.getMessage(), e);
		}
	}

	/**
	 * Method for save sequence into the file
	 * 
	 * @param sequence
	 *            sequence for export(save)
	 * @param path
	 *            path for the export file
	 * @throws DronSenderException
	 */
	public void exports(Sequence sequence, File file)
			throws DronSenderException {
		// Create new configuration file for new sequence
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, sequence);
		} catch (IOException e) {
			throw new DronSenderException(e.getMessage(), e);
		}
	}

	private File getFile(String path) throws DronSenderException {
		if (StringUtils.isBlank(path)) {
			throw new DronSenderException("Path can't be empty");
		}
		File file = new File(path);
		if (!file.isFile()) {
			throw new DronSenderException(String.format(
					"Path doesn't represent path for file: %s", path));
		}
		return file;
	}

}