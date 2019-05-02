package core.entity;

import java.util.ArrayList;

import core.MainApp;
import core.NoiseFlowField;
import core.PhysicsHandler;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class PhysicsObject {
	
	public PVector position = new PVector();
	public PVector velocity = new PVector();
	public PVector acceleration = new PVector();
	
	public ArrayList<ArrayList<PhysicsObject>> activeGroupList = new ArrayList<ArrayList<PhysicsObject>>();
	public NoiseFlowField flowField;
	public MainApp launcher;
	public PhysicsHandler handler;
	
	public int collisions = 0;
	public int cellsAway = 2;
	public float mass = 1;
	public float maxSpeed = 2;
	public float radius = 5;
	public int flowFieldIndex = 0;
	public boolean followNoiseField = true;
	public int color = 0;
	
	public abstract void draw();
	public abstract void update();
	
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
		
		correctionVector.setMag(mass);
		return correctionVector;
	}
	
	public void checkCollisions() {
		int index = flowField.getIndex(position);
		index = PApplet.constrain(index, 1, flowField.cols * flowField.rows) - 1; 
		
		if (flowFieldIndex != index) {
			// It will remove itself from that active list
			handler.removeObjectFromListOverField(this, flowFieldIndex);
			// Update its own index
			flowFieldIndex = index;
			
			if (followNoiseField) {
				// Grab the new force to follow
				PVector force = flowField.flowFieldForceAtIndex(flowFieldIndex);
				// Apply it to itself
				applyForce(force);
			}
			// Add itself to the new active list
			handler.addObjectToListOverField(this);
			
			activeGroupList.clear();
			
			int x = PApplet.floor(position.x / flowField.scale);
			int y = PApplet.floor(position.y / flowField.scale);
			
			// Checks particles at current position field square and surrounding squares
			// This repopulates the active group list
			for (int b = -cellsAway; b < cellsAway; b++) {
				for (int a = -cellsAway; a < cellsAway; a++) {
					int xa = (x + a) >= flowField.cols ? flowField.cols - 1: (x + a);
					int yb = (y + b) >= flowField.rows ? flowField.rows - 1: (y + b);
					if (xa < 0)
						xa = 0;
					if (yb < 0)
						yb = 0;
					activeGroupList.add(launcher.pHandler.getActiveFieldAt(flowField.getIndex(xa, yb)));
				}
			}
		} // end of if index changed
		
		for (ArrayList<PhysicsObject> list : activeGroupList) {
			for (PhysicsObject p : list) {
				if (checkOverlap(p)) {
					p.applyForce(getCollisionForce(p));
					collisions ++;
				}
				if (MainApp.drawCollisionLines) {
					launcher.noFill();
					launcher.strokeWeight(2);
					launcher.stroke(color);
					if (getDistance(p.position, position) < 100)
						launcher.line(p.position.x, p.position.y, position.x, position.y);
				}
			}
		}
	}
}
