package core.entity;

import java.util.ArrayList;
import core.MainApp;
import core.NoiseFlowField;
import core.PhysicsHandler;
import processing.core.PVector;

public class Particle extends PhysicsObject {
	
	private ArrayList<ParticlePoint> particlePoints;

	public int timeInterval = 500;
	public int previousTime = 0;

	public Particle(MainApp launcher, NoiseFlowField flowField, PhysicsHandler handler, PVector position) {
		this.launcher = launcher;
		this.flowField = flowField;
		this.handler = handler;
		this.position = position;
		
		particlePoints = new ArrayList<ParticlePoint>();
		
		mass = 0.01f;
		timeInterval = (int) launcher.random(250, 750);
		radius = launcher.random(15f, 20f);
		cellsAway = 1;
		color = launcher.color(255, 100, 100);
	}

	public void draw() {
		if (MainApp.drawParticles) {
			launcher.noFill();
			launcher.strokeWeight(1);
			launcher.stroke(255);
			launcher.ellipse(position.x, position.y, radius, radius);
		}
		if (MainApp.drawPointFlow)
			drawPointFlow();
	}

	public void update() {
		checkEdges();
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
		launcher.stroke(225, 128, 225);
		
		for (ParticlePoint p : particlePoints) {
			launcher.point(p.position.x, p.position.y);
		}
	}
	
	public void dropPoint() {
		int timePassed = launcher.millis() - previousTime;
		if (timePassed > timeInterval) {
			collisions = 0;
			if (launcher.random(1) > 0.6f)
				particlePoints.add(new ParticlePoint(position.copy()));
			previousTime = launcher.millis();
		}
	}

	public void checkEdges() {
		if (position.x > launcher.width) {
			position.x = 0;
		}
		if (position.x < 0) {
			position.x = launcher.width;
		}
		// when we reach the bottom of the screen reset the points
		if (position.y > launcher.height) {
			position.y = 0;
			particlePoints.clear();
		}
		if (position.y < 0) {
			position.y = launcher.height;
		}
	}
}
