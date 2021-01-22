package com.openmine.client.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.openmine.client.window.Window;

public class Camera {
	
	public static final float Z_NEAR = 0.1F;
	
	private static final float X_ANGLE_OFFSET = (float) -(Math.PI / 2);
	
	private Vector3f rotation;
	private Vector3f position;
	private Vector3f direction;
	private Vector3f right;
	
	private float fov;
	private float zFar;
	
	private Matrix4f modelMatrix;
	private Matrix4f viewMatrix;
	
	public Camera(Vector3f postion, float fov, float zFar) {
		this.rotation = new Vector3f(X_ANGLE_OFFSET, 0.0F, 0.0F);
		this.position = postion;
		this.direction = new Vector3f();
		this.right = new Vector3f();
		this.fov = (float) Math.toRadians(fov);
		this.zFar = zFar;
		
		modelMatrix = new Matrix4f().identity();
		viewMatrix = new Matrix4f().identity();
	}
	
	public void update(float delta) {
		
		viewMatrix.positiveZ(direction).negate().mul(delta);
		viewMatrix.positiveX(right).mul(delta);
		
		direction.z = 0.0F;
		right.z = 0.0F;
		
		viewMatrix.identity().rotateXYZ(rotation).translate(-position.x, -position.y, -position.z);
	}
	
	public Matrix4f getModelMatrix() {
		return modelMatrix;
	}
	
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}
	
	public Matrix4f getProjectionMatrix(float aspect) {
		return new Matrix4f().identity().perspective(fov, aspect, Z_NEAR, zFar);
	}
	
	public Matrix4f getMVPMatrix() {
		Matrix4f MVP = new Matrix4f();
		viewMatrix.mul(modelMatrix, MVP);
		getProjectionMatrix(Window.getWidth() / Window.getHeight()).mul(MVP, MVP);
		return MVP;
	}
	
	public void moveForward(float speed) {
		position.add(direction.mul(speed));
	}
	
	public void moveBackward(float speed) {
		position.sub(direction.mul(speed));
	}
	
	public void moveRightward(float speed) {
		position.add(right.mul(speed));
	}
	
	public void moveLeftward(float speed) {
		position.sub(right.mul(speed));
	}
	
	public void moveUpward(float speed) {
		position.add(0, 0, speed);
	}
	
	public void moveDownward(float speed) {
		position.sub(0, 0, speed);
	}
	
	public void rotateX(float angle) {
		rotation.z += angle;
	}
	
	public void rotateY(float angle) {
		if (((rotation.x - X_ANGLE_OFFSET) + angle) > Math.toRadians(-90.0F) && ((rotation.x - X_ANGLE_OFFSET) + angle) < Math.toRadians(90.0F))
			rotation.x += angle;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getDirection() {
		return direction;
	}
}
