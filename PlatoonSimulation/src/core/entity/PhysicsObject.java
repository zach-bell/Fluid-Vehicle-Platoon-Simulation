package core.entity;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class PhysicsObject {
	
	public PVector position = new PVector();
	public PVector velocity = new PVector();
	public PVector acceleration = new PVector();
	public float mass = 1;
	public float radius = 5;
	public int flowFieldIndex = 0;
	
	public abstract void draw();
	public abstract void update();
	public abstract void checkCollisions();
	
	public float getDistance(PVector dis1, PVector dis2) {
		return PApplet.sqrt(PApplet.pow((dis2.x - dis1.x), 2) + PApplet.pow((dis2.y - dis1.y), 2));
	}
	
	public void applyForce(PVector force) {
		acceleration.add(force);
	}
	
	public boolean checkOverlap(PhysicsObject other) {
		float edgeDistance = radius + other.radius;
		float distanceBetween = getDistance(other.position, position);
		if ((distanceBetween <= edgeDistance) & (distanceBetween > 0)) {
			return true;
		}
		return false;
	}
	
	public PVector getCollisionForce(PhysicsObject p) {
		PVector distanceVect = PVector.sub(p.position, position);
		float distanceVectMag = distanceVect.mag();
		float minDistance = radius + p.radius;
		
		float distanceCorrection = (minDistance - distanceVectMag) / 2.0f;
		PVector d = distanceVect.copy();
		PVector correctionVector = d.normalize().mult(distanceCorrection);
		
		correctionVector.setMag(p.mass);
		return correctionVector.mult(p.mass);
	}
}
