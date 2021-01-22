package com.openmine.client.window;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard {

	private static boolean[] keyDown = new boolean[GLFW_KEY_LAST + 1];

	public static void init(long windowHandle) {
		glfwSetInputMode(windowHandle, GLFW_STICKY_KEYS, GLFW_TRUE);

		glfwSetKeyCallback(windowHandle, keyCallback);
	}

	public static void cleanup() {
		keyCallback.free();
	}

	public static boolean isKeyDown(int key) {
		return keyDown[key];
	}

	private static GLFWKeyCallback keyCallback = new GLFWKeyCallback() {

		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			keyDown[key] = action == GLFW_PRESS || action == GLFW_REPEAT;
		}
	};
}
