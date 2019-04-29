package core.entity;

import java.util.ArrayList;

import core.MainApp;
import core.NoiseFlowField;
import core.PhysicsHandler;
import processing.core.PApplet;
import processing.core.PVector;

public class Particle extends PhysicsObject {

	private MainApp launcher;
	private NoiseFlowField flowField;
	private PhysicsHandler handler;

	public ArrayList<Particle> activeGroup = null;
	public float maxSpeed = 2;

	public Particle(MainApp launcher, NoiseFlowField flowField, PhysicsHandler handler, PVector position) {
		this.launcher = launcher;
		this.flowField = flowField;
		this.handler = handler;
		this.position = position;

		velocity = new PVector();
		acceleration = new PVector();
		mass = 0.2f;
		radius = 15f;
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
		followField(flowField.flowField);
		checkCollisions();

		velocity.add(acceleration);
		velocity.limit(maxSpeed);
		position.add(velocity);
		acceleration.mult(0);
	}

	public void followField(PVector[] field) {
		int index = flowField.getIndex(position);
		index = PApplet.constrain(index, 1, flowField.cols * flowField.rows) - 1;

		// The particle knows it's changed its index
		if (flowFieldIndex != index) {
			// It will remove itself from that active list
			handler.removeParticleFromListOverField(this, flowFieldIndex);
			// Update its own index
			flowFieldIndex = index;
			// Grab the new force to follow
			PVector force = field[flowFieldIndex];
			// Apply it to itself
			applyForce(force.mult(mass));
			// Add itself to the new active list
			handler.addParticleToListOverField(this);
		}
	}

	public void drawCollisionLines(ArrayList<Particle> fieldList) {
		for (Particle p : fieldList) {
			launcher.noFill();
			launcher.strokeWeight(1);
			launcher.stroke(255, 100, 100);
			if (getDistance(p.position, position) < 100)
				launcher.line(p.position.x, p.position.y, position.x, position.y);
		}
	}

	public void checkCollisions() {
		if (activeGroup != null) {
			for (Particle p : activeGroup) {
				if (checkOverlap(p)) {
					p.applyForce(getCollisionForce(p));
				}
			}
		}
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
