#version 430 core

layout(location = 0) in vec3 in_vertex;
layout(location = 1) in vec2 in_texcoords;

out vec2 texcoords;

uniform mat4 ModelMatrix;
uniform mat4 ViewMatrix;
uniform mat4 ProjectionMatrix;

uniform vec3 BlockPosition;
uniform vec2 ChunkPosition;

void main(void) {
	mat4 MVP = ProjectionMatrix * ViewMatrix * ModelMatrix;

	gl_Position = MVP * vec4((in_vertex + BlockPosition) + (vec3(ChunkPosition, 0) * 16.), 1);

	texcoords = in_texcoords;
}
