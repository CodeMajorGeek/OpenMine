package com.openmine.client.block;

import com.openmine.client.renderer.shader.BasicBlockShader;

public class DirtBlock implements Block {

	@Override
	public String getBlockName() {
		return "dirt_block";
	}

	@Override
	public String getTextureName() {
		return "blocks.dirt";
	}

	@Override
	public String getShaderName() {
		return BasicBlockShader.SHADER_NAME;
	}
}
