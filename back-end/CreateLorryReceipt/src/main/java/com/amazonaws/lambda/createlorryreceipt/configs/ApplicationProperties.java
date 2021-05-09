package com.amazonaws.lambda.createlorryreceipt.configs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperties {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProperties.class);

	private static Properties properties = null;

	private ApplicationProperties() {
	}

	private static void loadProperties() throws IOException {

		properties = new Properties();
		ClassLoader classLoader = ApplicationProperties.class.getClassLoader();

		File file = new File(classLoader.getResource("application.properties").getFile());
		FileInputStream fileInput = new FileInputStream(file);
		properties.load(fileInput);
	}

	public static String getProperty(String key) {

		if (properties == null) {
			try {
				loadProperties();
			} catch (IOException e) {
				LOGGER.error("Properties load failed");
				e.printStackTrace();
			}
		}

		return (String) properties.get(key);
	}
}
