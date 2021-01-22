package com.openmine.client.window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

public class Window {
	
	private static final Logger LOGGER = LogManager.getLogger(Window.class);
	
	private static int width;
	private static int height;
	
	private static long windowHandle = NULL;
	
	public static void createWindow(String title, int width, int height) {
		LOGGER.log(Level.INFO, "Creating the Window !");
		long lastTime = System.currentTimeMillis();
		
		Window.width = width;
		Window.height = height;
		
		if (!glfwInit()) {
			LOGGER.catching(new RuntimeException("Cannot initialize GLFW !"));
			System.exit(-1);
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
		
		if (windowHandle == NULL) {
			LOGGER.catching(new RuntimeException("Cannot create a GLFW window !"));
			System.exit(-1);
		}
		
		glfwSetWindowSizeCallback(windowHandle, windowSizeCallback);
		
		Mouse.init(windowHandle);
		Keyboard.init(windowHandle);
		
		glfwMakeContextCurrent(windowHandle);
		GL.createCapabilities();
		
		glfwSwapInterval(GLFW_FALSE);
		
		LOGGER.log(Level.INFO, "Window created in " + (System.currentTimeMillis() - lastTime) + " ms !");
	}
	
	public static void destroyWindow() {
		
		windowSizeCallback.free();
		Mouse.cleanup();
		Keyboard.cleanup();
		
		glfwDestroyWindow(windowHandle);
		GL.destroy();
		glfwTerminate();
	}
	
	public static void update() {
		glfwPollEvents();
		glfwSwapBuffers(windowHandle);
	}
	
	public static boolean shouldClose() {
		return glfwWindowShouldClose(windowHandle);
	}
	
	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
	
	private static GLFWWindowSizeCallback windowSizeCallback = new GLFWWindowSizeCallback() {

		@Override
		public void invoke(long window, int width, int height) {
			Window.width = width;
			Window.height = height;
			
			// glViewport(0, 0, width, height);
		}
	};
}
