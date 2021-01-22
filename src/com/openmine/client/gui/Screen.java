package com.openmine.client.gui;

public interface Screen {
	
	void update(float delta);
	void render();
	void cleanup();
}
