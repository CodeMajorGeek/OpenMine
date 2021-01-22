package com.openmine.client.renderer;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.openmine.client.renderer.shader.BasicBlockShader;
import com.openmine.client.renderer.shader.Shader;

public class ShaderManager {
	private static final Logger LOGGER = LogManager.getLogger(ShaderManager.class);
	
	private static Map<String, Shader> loadedShaders = new HashMap<>();
	
	public static void loadAllShaders() {
		loadedShaders.put("basicBlock", new BasicBlockShader());
	}
	
	public static void cleanupAllShaders() {
		for (Shader shader : loadedShaders.values())
			shader.cleanup();
	}
	
	public static Shader getShader(String shaderName) {
		if (!loadedShaders.containsKey(shaderName)) {
			LOGGER.catching(new RuntimeException("Cannot find shader named \"" + shaderName + "\" !"));
			System.exit(-1);
		}
		
		return loadedShaders.get(shaderName);
	}
}
