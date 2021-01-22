package com.openmine.client.gui;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL43.*;

import org.joml.Vector3f;

import com.openmine.client.renderer.Camera;
import com.openmine.client.renderer.ShaderManager;
import com.openmine.client.renderer.WorldRenderer;
import com.openmine.client.renderer.shader.Shader;
import com.openmine.client.settings.GraphicsSettings;
import com.openmine.client.window.Keyboard;
import com.openmine.client.window.Mouse;

public class InGameScreen implements Screen {

	private Camera camera = new Camera(new Vector3f(8.0F, 8.0F, 5.0F), GraphicsSettings.FOV,
			GraphicsSettings.MAX_CHUNK_DISTANCE * 16.0F);

	private Shader shader;

	private WorldRenderer worldRenderer;

	public InGameScreen() {
		shader = ShaderManager.getShader("basicBlock");
		worldRenderer = new WorldRenderer();

		Mouse.setGrabbed(true);

		glDepthFunc(GL_LESS);
		glEnable(GL_DEPTH_TEST);
	}

	@Override
	public void update(float delta) {
		if (Keyboard.isKeyDown(GLFW_KEY_W))
			camera.moveForward(0.01F);

		if (Keyboard.isKeyDown(GLFW_KEY_S))
			camera.moveBackward(0.01F);

		if (Keyboard.isKeyDown(GLFW_KEY_Q))
			camera.moveLeftward(0.01F);

		if (Keyboard.isKeyDown(GLFW_KEY_D))
			camera.moveRightward(0.01F);

		if (Keyboard.isKeyDown(GLFW_KEY_SPACE))
			camera.moveUpward(0.2F);

		if (Keyboard.isKeyDown(GLFW_KEY_LEFT_SHIFT))
			camera.moveDownward(0.2F);

		camera.rotateX(Mouse.getDX() * 0.001F);
		camera.rotateY(Mouse.getDY() * 0.001F);

		camera.update(delta);
		shader.applyCamera(camera);

		worldRenderer.update(camera);
	}
	
	private long lastFPS = System.currentTimeMillis();
	private int fpsCount = 0;
	
	@Override
	public void render() {

		long lastTime = System.currentTimeMillis();
		worldRenderer.render();
		// System.out.println("Rendering took: " + (System.currentTimeMillis() - lastTime) + " ms.");
		
		
		if (System.currentTimeMillis() >= lastFPS + 1000) {
			System.out.println("FPS: " + fpsCount);
			lastFPS = System.currentTimeMillis();
			fpsCount = 0;
		} else
			fpsCount++;
		
	}

	@Override
	public void cleanup() {
		worldRenderer.cleanup();
	}

}
