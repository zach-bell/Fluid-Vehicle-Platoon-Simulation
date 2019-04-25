package core.entity;

import core.NoiseFlowField;
import processing.core.PApplet;
import processing.core.PVector;

public class Particle {

	private PApplet launcher;
	private NoiseFlowField flowField;
	
	public PVector position, velocity, acceleration;
	public float radius = 15;
	public float maxSpeed = 3;
	public float mass = 0.1f;

	public Particle(PApplet launcher, NoiseFlowField flowField, PVector position) {
		this.launcher = launcher;
		this.flowField = flowField;
		this.position = position;
		
		velocity = new PVector();
		acceleration = new PVector();
	}

	public void draw() {
		launcher.noStroke();
		launcher.fill(255);
		launcher.ellipse(position.x, position.y, radius, radius);
	}

	public void update() {
		checkEdges();
		followField(flowField.flowField);
		
		velocity.add(acceleration);
		velocity.limit(maxSpeed);
		position.add(velocity);
		acceleration.mult(0);
	}
	
	public void followField(PVector[] field) {
		int x = PApplet.floor(position.x / flowField.scale);
		int y = PApplet.floor(position.y / flowField.scale);
		int index = x + y * flowField.cols;
		index = PApplet.constrain(index, 1, flowField.cols * flowField.rows) - 1;
		PVector force = field[index];
		applyForce(force.mult(mass));
	}
	
	public void checkEdges() {
		if (position.x > launcher.width)
			position.x = 0;
		if (position.x < 0)
			position.x = launcher.width;
		if (position.y > launcher.height)
			position.y = 0;
		if (position.y < 0)
			position.y = launcher.height;
	}
	
	public void applyForce(PVector force) {
		acceleration.add(force);
	}
}
