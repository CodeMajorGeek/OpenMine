package com.openmine.client.renderer.mesh;

public class BlockMesh implements Mesh {

	private static final float[] BLOCK_VERTICES = {
			0.0F, 0.0F, 0.0F,
			1.0F, 0.0F, 0.0F,
			1.0F, 1.0F, 0.0F,
			0.0F, 1.0F, 0.0F,
			1.0F, 0.0F, 1.0F,
			0.0F, 0.0F, 1.0F,
			1.0F, 1.0F, 1.0F,
			0.0F, 1.0F, 1.0F
	};

	private static final float[] BLOCK_TEX_COORDS = {
			0.0F, 0.0F,
			1.0F, 0.0F,
			1.0F, 1.0F,
			0.0F, 1.0F,
			1.0F, 1.0F,
			1.0F, 0.0F,
			0.0F, 1.0F,
			0.0F, 0.0F
	};

	private static final int[] BLOCK_INDICES = {
			0, 1, 2, 2, 3, 0,
			0, 1, 4, 4, 5, 0,
			1, 2, 6, 6, 4, 1,
			2, 3, 7, 7, 6, 2,
			3, 0, 5, 5, 7, 3,
			5, 4, 6, 6, 7, 5
	};
	
	@Override
	public float[] getVertices() {
		return BLOCK_VERTICES;
	}

	@Override
	public float[] getTexCoords() {
		return BLOCK_TEX_COORDS;
	}

	@Override
	public int[] getIndices() {
		return BLOCK_INDICES;
	}

	@Override
	public int getCount() {
		return BLOCK_INDICES.length;
	}

}
