package core.entity;

import java.util.ArrayList;

import core.MainApp;
import core.NoiseFlowField;
import core.PhysicsHandler;
import processing.core.PApplet;
import processing.core.PVector;

public class Particle extends PhysicsObject {
	
	ArrayList<ParticlePoint> particlePoints;

	public Particle(MainApp launcher, NoiseFlowField flowField, PhysicsHandler handler, PVector position) {
		this.launcher = launcher;
		this.flowField = flowField;
		this.handler = handler;
		this.position = position;
		
		particlePoints = new ArrayList<ParticlePoint>();
		
		mass = 0.01f;
		radius = 15f;
		cellsAway = 1;
		color = launcher.color(255, 100, 100);
	}

	public void draw() {
		if (MainApp.drawParticles) {
			launcher.noFill();
			launcher.stroke(255);
			launcher.ellipse(position.x, position.y, radius, radius);
		}
		if (MainApp.drawPointFlow)
			drawPointFlow();
	}

	public void update() {
		checkEdges();
		
		int index = flowField.getIndex(position);
		index = PApplet.constrain(index, 1, flowField.cols * flowField.rows) - 1;
		if (flowFieldIndex != index)
			dropPoint();
		
		checkCollisions();
		applyForce(MainApp.globalForce);

		velocity.add(acceleration);
		velocity.limit(maxSpeed);
		velocity.mult(0.99f);
		position.add(velocity);
		acceleration.mult(0);
	}
	
	public void drawPointFlow() {
		launcher.stroke(128, 225, 225);
		
		for (ParticlePoint p : particlePoints) {
			launcher.point(p.position.x, p.position.y);
		}
	}
	
	public void dropPoint() {
		if (launcher.random(1) > 0.5f)
			particlePoints.add(new ParticlePoint(position.copy()));
	}

	public void checkEdges() {
		if (position.x > launcher.width) {
			position.x = 0;
			particlePoints.clear();
		}
		if (position.x < 0) {
			position.x = launcher.width;
			particlePoints.clear();
		}
		// when we reach the bottom of the screen reset the points
		if (position.y > launcher.height) {
			position.y = 0;
			particlePoints.clear();
		}
		if (position.y < 0) {
			position.y = launcher.height;
			particlePoints.clear();
		}
	}
}
