package com.openmine.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtils {
	
	private static final Logger LOGGER = LogManager.getLogger(FileUtils.class);
	
	public static InputStream getInputStream(String assetPath) {
		return FileUtils.class.getResourceAsStream(assetPath);	
	}
	
	public static String getAssetsSources(String assetPath) {
		String sources = "";
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream(assetPath)))) {
			String line = null;
			while ((line = reader.readLine()) != null)
				sources += line + "\n";
		} catch (IOException e) {
			LOGGER.catching(e);
			System.exit(-1);
		}
		
		return sources;
	}
}
