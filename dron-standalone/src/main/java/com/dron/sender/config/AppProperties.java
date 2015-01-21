package com.dron.sender.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AppProperties {

	@Value("${file.path}")
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

}
