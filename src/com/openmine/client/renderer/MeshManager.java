package com.openmine.client.renderer;

import static org.lwjgl.opengl.GL43.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.openmine.client.renderer.mesh.Mesh;

public class MeshManager {
	private static final Logger LOGGER = LogManager.getLogger(MeshManager.class);
	
	private static Map<Integer, int[]> loadedVAOs = new HashMap<>();
	
	public static int loadMesh(Mesh mesh) {
		int vaoHandle = glGenVertexArrays();
		
		int[] vboHandles = new int[3];
		glGenBuffers(vboHandles);
		
		glBindVertexArray(vaoHandle);
		
		glBindBuffer(GL_ARRAY_BUFFER, vboHandles[0]);
		glBufferData(GL_ARRAY_BUFFER, mesh.getVertices(), GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, vboHandles[1]);
		glBufferData(GL_ARRAY_BUFFER, mesh.getTexCoords(), GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindVertexArray(0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboHandles[2]);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, mesh.getIndices(), GL_STATIC_DRAW);
		
		loadedVAOs.put(vaoHandle, vboHandles);
		
		return vaoHandle;
	}
	
	public static void cleanupAllMeshs() {
		for (Entry<Integer, int[]> entry : loadedVAOs.entrySet()) {
			glDeleteVertexArrays(entry.getKey());
			glDeleteBuffers(entry.getValue());
		}
		loadedVAOs.clear();
	}
	
	public static void cleanupMesh(int vaoHandle) {
		if (!loadedVAOs.containsKey(vaoHandle)) {
			LOGGER.catching(new RuntimeException("Cannot find " + vaoHandle + " VAO !"));
			System.exit(-1);
		}
		glDeleteVertexArrays(vaoHandle);
		glDeleteBuffers(loadedVAOs.get(vaoHandle));
		loadedVAOs.remove(vaoHandle);
	}
	
	public static int getIBOHandle(int vaoHandle) {
		if (!loadedVAOs.containsKey(vaoHandle)) {
			LOGGER.catching(new RuntimeException("Cannot find " + vaoHandle + " VAO !"));
			System.exit(-1);
		}
		return loadedVAOs.get(vaoHandle)[2];
	}
}
