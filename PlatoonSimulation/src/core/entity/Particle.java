package core.entity;

import java.util.ArrayList;

import core.NoiseFlowField;
import core.ParticleHandler;
import processing.core.PApplet;
import processing.core.PVector;

public class Particle {

	private PApplet launcher;
	private NoiseFlowField flowField;
	private ParticleHandler handler;

	public PVector position, velocity, acceleration;
	public ArrayList<Particle> activeGroup = null;
	public float radius = 15f;
	public float maxSpeed = 2;
	public float mass = 0.1f;
	public int flowFieldIndex = 0;

	public Particle(PApplet launcher, NoiseFlowField flowField, ParticleHandler handler, PVector position) {
		this.launcher = launcher;
		this.flowField = flowField;
		this.handler = handler;
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
		checkCollisions();

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

		if (flowFieldIndex != index) {
			handler.removeParticleFromListOverField(this, flowFieldIndex);
			flowFieldIndex = index;
			PVector force = field[flowFieldIndex];
			applyForce(force.mult(mass));
			handler.addParticleToListOverField(this);
		}
	}

	public void drawCollisionLines(ArrayList<Particle> fieldList) {
		for (Particle p : fieldList) {
			launcher.noFill();
			launcher.stroke(255, 100, 100);
			if (getDistance(p.position, position) < 100)
				launcher.line(p.position.x, p.position.y, position.x, position.y);
		}
	}

	public float getDistance(PVector dis1, PVector dis2) {
		return PApplet.sqrt(PApplet.pow((dis2.x - dis1.x), 2) + PApplet.pow((dis2.y - dis1.y), 2));
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

	private boolean checkOverlap(Particle other) {
		float edgeDistance = radius + other.radius;
		float distanceBetween = getDistance(other.position, position);
		if ((distanceBetween <= edgeDistance) & (distanceBetween > 0)) {
			return true;
		}
		return false;
	}

	private PVector getCollisionForce(Particle p) {
		PVector distanceVect = PVector.sub(p.position, position);
		float distanceVectMag = distanceVect.mag();
		float minDistance = radius + p.radius;
		
		float distanceCorrection = (minDistance - distanceVectMag) / 2.0f;
		PVector d = distanceVect.copy();
		PVector correctionVector = d.normalize().mult(distanceCorrection);
		
		correctionVector.setMag(p.mass);
		return correctionVector.mult(p.mass);
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
