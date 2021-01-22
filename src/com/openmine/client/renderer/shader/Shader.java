package com.openmine.client.renderer.shader;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.FloatBuffer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import com.openmine.client.renderer.Camera;
import com.openmine.client.util.FileUtils;
import com.openmine.client.window.Window;

public class Shader {
	
	private static final Logger LOGGER = LogManager.getLogger(Shader.class);
	
	private static final String SHADERS_PATH = "/shaders/";
	
	private String shaderName;
	
	private int vertexHandle;
	private int fragmentHandle;
	private int programHandle;
	
	private int modelUniformHandle;
	private int viewUniformHandle;
	private int projectionUniformHandle;
	private int blockPositionUniformHandle;
	private int chunkPositionUniformHandle;
	
	public Shader(String shaderFileName, String shaderName) {
		this.shaderName = shaderName;
		
		vertexHandle = createShader(SHADERS_PATH + shaderFileName + ".vert", GL_VERTEX_SHADER);
		fragmentHandle = createShader(SHADERS_PATH + shaderFileName + ".frag", GL_FRAGMENT_SHADER);
		programHandle = glCreateProgram();
		
		glAttachShader(programHandle, vertexHandle);
		glAttachShader(programHandle, fragmentHandle);
		glLinkProgram(programHandle);
		
		modelUniformHandle = getUniformHandle("ModelMatrix");
		viewUniformHandle = getUniformHandle("ViewMatrix");
		projectionUniformHandle = getUniformHandle("ProjectionMatrix");
		blockPositionUniformHandle = getUniformHandle("BlockPosition");
		chunkPositionUniformHandle = getUniformHandle("ChunkPosition");
	}
	
	public void bind() {
		glUseProgram(programHandle);
	}
	
	public void cleanup() {
		glDeleteProgram(programHandle);
		glDeleteShader(vertexHandle);
		glDeleteShader(fragmentHandle);
	}
	
	public int getUniformHandle(String name) {
		return glGetUniformLocation(programHandle, name);
	}
	
	public static void setUniform(int uniformHandle, Matrix4f mat4) {
		FloatBuffer fb = memAllocFloat(16);
		mat4.get(fb);
		
		glUniformMatrix4fv(uniformHandle, false, fb);
		
		memFree(fb);
	}
	
	public static void setUniform(int uniformHandle, Vector3f vec3) {
		glUniform3f(uniformHandle, vec3.x, vec3.y, vec3.z);
	}
	
	public static void setUniform(int uniformHandle, Vector2f vec2) {
		glUniform2f(uniformHandle, vec2.x, vec2.y);
	}
	
	public void applyCamera(Camera camera) {
		setUniform(modelUniformHandle, camera.getModelMatrix());
		setUniform(viewUniformHandle, camera.getViewMatrix());
		setUniform(projectionUniformHandle, camera.getProjectionMatrix((float) Window.getWidth() / Window.getHeight()));
	}
	
	public void setBlockPosition(Vector3f pos) {
		setUniform(blockPositionUniformHandle, pos);
	}
	
	public void setChunkPosition(Vector2f pos) {
		setUniform(chunkPositionUniformHandle, pos);
	}
	
	public String getShaderName() {
		return shaderName;
	}
	
	private int createShader(String shaderPath, int type) {
		int shaderHandle = glCreateShader(type);
		glShaderSource(shaderHandle, FileUtils.getAssetsSources(shaderPath));
		glCompileShader(shaderHandle);
		
		String shaderInfo = glGetShaderInfoLog(shaderHandle);
		if (shaderInfo.length() > 0) {
			LOGGER.log(Level.ERROR, "Error while compiling \"" + shaderPath + "\":\n" + shaderInfo);
			System.exit(-1);
		}
		
		return shaderHandle;
	}
}
