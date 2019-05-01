package core.entity;

import core.MainApp;
import core.NoiseFlowField;
import core.PhysicsHandler;
import processing.core.PVector;

public class Particle extends PhysicsObject {

	public Particle(MainApp launcher, NoiseFlowField flowField, PhysicsHandler handler, PVector position) {
		this.launcher = launcher;
		this.flowField = flowField;
		this.handler = handler;
		this.position = position;

		mass = 0.01f;
		radius = 15f;
		cellsAway = 1;
	}

	public void draw() {
		if (MainApp.drawParticles) {
			launcher.noFill();
			launcher.stroke(255);
			launcher.ellipse(position.x, position.y, radius, radius);
		}
	}

	public void update() {
		checkEdges();
		checkCollisions();
		applyForce(MainApp.globalForce);

		velocity.add(acceleration);
		velocity.limit(maxSpeed);
		velocity.mult(0.99f);
		position.add(velocity);
		acceleration.mult(0);
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
}
