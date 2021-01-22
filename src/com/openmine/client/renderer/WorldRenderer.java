package com.openmine.client.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.openmine.client.chunk.Chunk;
import com.openmine.client.chunk.FlatChunk;

public class WorldRenderer {

	private ChunkRenderer chunkRenderer;

	private List<Entry<Vector2f, Chunk>> chunks = new ArrayList<>();
	private List<Entry<Vector2f, Chunk>> renderableChunks = new ArrayList<>();

	public WorldRenderer() {
		chunkRenderer = new ChunkRenderer();
	}

	public void update(Camera camera) {

		// add not created renderable chunks
		Vector2f playerChunkPosition = getPlayerChunkPosition(camera);
		if (shouldCreate(playerChunkPosition, camera) && chunks.contains())
			chunks.add(new FlatChunk(playerChunkPosition));

		// render or not already created chunks
		for (Chunk chunk : chunks)
			if (shouldRender(chunk, camera) && !renderableChunks.contains(chunk))
				renderableChunks.add(chunk);
			else if (!shouldRender(chunk, camera) && renderableChunks.contains(chunk))
				renderableChunks.remove(chunk);
	}

	public void render() {
		for (Chunk chunk : renderableChunks)
			chunkRenderer.render(chunk);
	}

	public void cleanup() {
		chunkRenderer.cleanup();
	}

	private Vector2f getPlayerChunkPosition(Camera camera) {
		Vector3f cameraPosition = camera.getPosition();
		return new Vector2f((int) (cameraPosition.x / Chunk.CHUNK_BORDER_SIZE),
				(int) (cameraPosition.y / Chunk.CHUNK_BORDER_SIZE));
	}

	private boolean shouldCreate(Vector2f chunkPosition, Camera camera) {
		Vector4f realGlPos = new Vector4f(0.F, 0.F, 0.F, 1.F).mul(camera.getMVPMatrix());
		if (Math.abs(realGlPos.x) < realGlPos.w && Math.abs(realGlPos.z) < realGlPos.w)
			return true;
		return false;
	}

	private boolean shouldRender(Chunk chunk, Camera camera) {
		for (Vector3f blockPos : chunk.getBlockPositions()) {
			Vector4f realGlPos = new Vector4f(blockPos.x, blockPos.y, blockPos.z, 1.F).mul(camera.getMVPMatrix());
			if (Math.abs(realGlPos.x) < realGlPos.w && Math.abs(realGlPos.z) < realGlPos.w)
				return true;
		}
		return false;
	}
}
