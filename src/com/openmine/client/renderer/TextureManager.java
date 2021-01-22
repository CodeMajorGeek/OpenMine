package com.openmine.client.renderer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureManager {
	private static final Logger LOGGER = LogManager.getLogger(TextureManager.class);
	
	private static Map<String, Texture> loadedTextures = new HashMap<>();
	
	public static void loadTexture(String textureName) {
		if (!loadedTextures.containsKey(textureName))
			try {
				loadedTextures.put(textureName, new Texture(textureName));
			} catch (IOException e) {
				LOGGER.catching(e);
				System.exit(-1);
			}
	}
	
	public static void cleanupAllTextures() {
		for (Texture texture : loadedTextures.values())
			texture.cleanup();
	}
}
