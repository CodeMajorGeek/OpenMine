package com.openmine.client;

import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.glfw.GLFW.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.openmine.client.gui.InGameScreen;
import com.openmine.client.gui.Screen;
import com.openmine.client.renderer.MeshManager;
import com.openmine.client.renderer.ShaderManager;
import com.openmine.client.renderer.TextureManager;
import com.openmine.client.window.Keyboard;
import com.openmine.client.window.Window;

public class OpenMine {
	
	private static final Logger LOGGER = LogManager.getLogger(OpenMine.class);
	
	private static final String VERSION = "0.0.0-alpha";
	private static final String TITLE = "OpenMine v." + VERSION;
	
	private static final int DEFAULT_WIDTH = 1080;
	private static final int DEFAULT_HEIGHT = 720;
	
	private static Screen currentScreen;
	
	public static void changeScreen(Screen newScreen) {
		currentScreen.cleanup();
		currentScreen = newScreen;
	}
	
	public static void main(String[] args) {
		LOGGER.log(Level.INFO, "Starting OpenMine...");
		Window.createWindow(TITLE, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		LOGGER.log(Level.INFO, "Loading all shaders...");
		long lastTime = System.currentTimeMillis();
		ShaderManager.loadAllShaders();
		LOGGER.log(Level.INFO, "All shaders loaded in " + (System.currentTimeMillis() - lastTime) + " ms !");
		
		currentScreen = new InGameScreen();
		
		LOGGER.log(Level.INFO, "Entering Game loop.");
		lastTime = System.currentTimeMillis();
		while (!Window.shouldClose() && !Keyboard.isKeyDown(GLFW_KEY_ESCAPE)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			long currentTime = System.currentTimeMillis();
			currentScreen.update(currentTime - lastTime);
			currentScreen.render();
			
			Window.update();
			lastTime = currentTime;
		}
		
		LOGGER.log(Level.INFO, "Cleaning all textures...");
		lastTime = System.currentTimeMillis();
		TextureManager.cleanupAllTextures();
		LOGGER.log(Level.INFO, "All textures cleaned-up in " + (System.currentTimeMillis() - lastTime) + " ms !");
		
		LOGGER.log(Level.INFO, "Cleaning all meshs...");
		lastTime = System.currentTimeMillis();
		MeshManager.cleanupAllMeshs();
		LOGGER.log(Level.INFO, "All meshs cleaned-up in " + (System.currentTimeMillis() - lastTime) + " ms !");
		
		LOGGER.log(Level.INFO, "Cleaning all shaders...");
		lastTime = System.currentTimeMillis();
		ShaderManager.cleanupAllShaders();
		LOGGER.log(Level.INFO, "All shaders cleaned-up in " + (System.currentTimeMillis() - lastTime) + " ms !");
		
		Window.destroyWindow();
		LOGGER.log(Level.INFO, "Goodbye !");
	}

}
