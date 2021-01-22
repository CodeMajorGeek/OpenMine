package com.openmine.client.window;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class Mouse {

	private static boolean grabbed = false;

	private static int x;
	private static int y;
	private static int dx;
	private static int dy;

	private static long windowHandle;

	public static void init(long windowHandle) {
		Mouse.windowHandle = windowHandle;

		glfwSetInputMode(windowHandle, GLFW_STICKY_MOUSE_BUTTONS, GLFW_TRUE);

		glfwSetCursorPosCallback(windowHandle, cursorPosCallback);
	}

	public static void cleanup() {
		cursorPosCallback.free();
	}

	public static void setGrabbed(boolean grabbed) {
		glfwSetInputMode(windowHandle, GLFW_CURSOR, grabbed ? GLFW_CURSOR_HIDDEN : GLFW_CURSOR_NORMAL);
		Mouse.grabbed = grabbed;
	}

	public static int getDX() {
		int tempDX = dx;
		dx = 0;
		return tempDX;
	}

	public static int getDY() {
		int tempDY = dy;
		dy = 0;
		return tempDY;
	}

	private static GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {

		@Override
		public void invoke(long window, double x, double y) {

			if (grabbed) {
				int xOffset = Window.getWidth() / 2;
				int yOffset = Window.getHeight() / 2;

				Mouse.x = xOffset;
				Mouse.y = yOffset;
				dx = (int) x - xOffset;
				dy = (int) y - yOffset;

				glfwSetCursorPos(windowHandle, xOffset, yOffset);
			} else {
				Mouse.x = (int) x;
				Mouse.y = (int) y;
				Mouse.dx = -1;
				Mouse.dy = -1;
			}
		}
	};
}
