package com.openmine.client.renderer.mesh;

public interface Mesh {
	
	float[] getVertices();
	float[] getTexCoords();
	int[] getIndices();
	
	int getCount();
}
