package com.openmine.client.chunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;

import com.openmine.client.block.Block;
import com.openmine.client.block.Blocks;

public class FlatChunk extends Chunk {

	@Override
	public Map<Block, List<Vector3f>> build() {
		Map<Block, List<Vector3f>> blocks = new HashMap<>();
		for (float x = 0.0F; x < CHUNK_BORDER_SIZE; x++)
			for (float y = 0.0F; y < CHUNK_BORDER_SIZE; y++)
				for (float z = 0.0F; z < 4.0F; z++) {
					if (!blocks.containsKey(Blocks.DIRT_BLOCK))
						blocks.put(Blocks.DIRT_BLOCK, new ArrayList<>());
					blocks.get(Blocks.DIRT_BLOCK).add(new Vector3f(x, y, z));
				}
		return blocks;
	}
}
