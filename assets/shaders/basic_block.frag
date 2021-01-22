#version 430 core

in vec2 texcoords;

out vec4 FragColor;

uniform sampler2D textureSampler;

void main(void) {
	FragColor = texture(textureSampler, texcoords);
}
