package com.openmine.client.chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joml.Vector3f;

import com.openmine.client.block.Block;

public abstract class Chunk {
	
	public static final float CHUNK_BORDER_SIZE = 16;
	
	private Map<Block, List<Vector3f>> blocks;

	public Chunk() {
		blocks = build();
	}
	
	public Block getBlockPosition(Vector3f position) {
		for (Entry<Block, List<Vector3f>> entry : blocks.entrySet())
			for (Vector3f vec3 : entry.getValue())
				if (vec3.equals(position))
					return entry.getKey();
		return null;
	}

	public Map<Block, List<Vector3f>> getPositionsPerBlocks() {
		return blocks;
	}

	public List<Block> getBlocks() {
		return new ArrayList<>(blocks.keySet());
	}

	public List<Vector3f> getBlockPositions() {
		List<Vector3f> blockPositions = new ArrayList<>();
		for (List<Vector3f> vec3s : blocks.values())
			for (Vector3f vec3 : vec3s)
				blockPositions.add(vec3);		
		return blockPositions;
	}
	
	public abstract Map<Block, List<Vector3f>> build();
}
