package com.openmine.client.renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.List;
import java.util.Map.Entry;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.openmine.client.block.Block;
import com.openmine.client.chunk.Chunk;
import com.openmine.client.renderer.mesh.BlockMesh;
import com.openmine.client.renderer.shader.Shader;

public class ChunkRenderer {

	private BlockMesh blockMesh;
	
	private int blockVAOHandle;
	private int blockIBOHandle;
	
	private Shader currentShader = null;
	
	public ChunkRenderer() {
		blockMesh = new BlockMesh();
		
		blockVAOHandle = MeshManager.loadMesh(blockMesh);
		blockIBOHandle = MeshManager.getIBOHandle(blockVAOHandle);
	}
	
	public void cleanup() {
		MeshManager.cleanupMesh(blockVAOHandle);
	}

	public void render(Vector2f chunkPosition, Chunk chunk) {
		
		for (Block block : chunk.getBlocks())
			TextureManager.loadTexture(block.getTextureName()); // TODO: make a good texture loader system
		
		glBindVertexArray(blockVAOHandle);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, blockIBOHandle);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		for (Entry<Block, List<Vector3f>> blockPos : chunk.getPositionsPerBlocks().entrySet()) {
			Block block = blockPos.getKey();
			if (currentShader == null || !currentShader.getShaderName().equals(block.getShaderName())) {
				currentShader = ShaderManager.getShader(block.getShaderName());
				currentShader.bind();
			}
			
			currentShader.setChunkPosition(chunkPosition);
			for (Vector3f pos : blockPos.getValue()) {
				currentShader.setBlockPosition(pos);
				glDrawElements(GL_TRIANGLES, blockMesh.getCount(), GL_UNSIGNED_INT, 0);
			}
		}
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
}
