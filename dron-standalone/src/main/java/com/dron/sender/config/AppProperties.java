package com.dron.sender.config;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AppProperties {

	@Value("${file.path}")
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(File file) {
		String filePath;
		if (file.isFile()) {
			filePath = StringUtils.substringBeforeLast(file.getPath(),
					File.separator);
		} else {
			filePath = file.getPath();
		}
		this.filePath = filePath;
	}

}
