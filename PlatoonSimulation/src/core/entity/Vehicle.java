package core.entity;

import java.util.ArrayList;

import core.MainApp;
import core.NoiseFlowField;
import processing.core.PApplet;
import processing.core.PVector;

public class Vehicle extends PhysicsObject {
	
	public PVector position;
	public PVector[] points;
	
	public float size = 3.25f;
	public float rotationSpeed = 0.02f;
	public float speed = 2;
	public float maxSpeed = 4;
	public float rotation = 0;
	public int collisions = 0;
	public boolean leader = false;
	
	private MainApp launcher;
	private NoiseFlowField flowField;
	
	public Vehicle(MainApp launcher, float x, float y) {
		this.position = new PVector(x, y);
		this.launcher = launcher;
		
		flowField = launcher.getNoiseField();
		velocity = new PVector();
		acceleration = new PVector();
		radius = 40;
		mass = 100;
	}
	
	public Vehicle(MainApp launcher, float x, float y, boolean leader) {
		this.position = new PVector(x, y);
		this.launcher = launcher;
		this.leader = leader;
		
		flowField = launcher.getNoiseField();
		velocity = new PVector();
		acceleration = new PVector();
		radius = 25;
		mass = 100;
	}
	
	// creates the initial points for the shape to follow
	private void setPoints() {
		points = new PVector[] {
				new PVector(0, (10 * size)),
				new PVector(5 * size, -(5 * size)),
//				new PVector(0, -(2 * size)),
				new PVector(-(5 * size), -(5 * size)),
				new PVector(0, (10 * size))};
	}
	
	public void draw() {
		if (MainApp.drawParticles) {
			launcher.noFill();
			launcher.stroke(255, 255, 0);
			launcher.ellipse(position.x, position.y, radius, radius);
		}
		if(MainApp.drawVehicles) {
			launcher.pushMatrix();	// push matrix to allow translations or rotations
			launcher.stroke(255,0,0);
			launcher.fill(25);
			launcher.strokeWeight(2);
			launcher.beginShape();
			// Add verticies of the shape
			for (PVector point : points) {
				launcher.vertex(point.x, point.y);
			}
			launcher.translate(position.x, position.y);	// translate to position
			launcher.rotate(PApplet.PI);	// rotate 180 degrees
			launcher.endShape();
			launcher.popMatrix();
		}
		launcher.noStroke();
		launcher.fill(220);
		launcher.textSize(10);
		launcher.text("" + collisions, position.x, position.y);
	}
	
	public void update() {
		int index = flowField.getIndex(position);
		index = PApplet.constrain(index, 1, flowField.cols * flowField.rows) - 1; 
		
		if (flowFieldIndex != index) {
			flowFieldIndex = index;
		}
		velocity.add(acceleration);
		velocity.limit(maxSpeed);
		position.add(velocity);
		if (position.x > launcher.width)
			position.x = launcher.width;
		if (position.x < 0)
			position.x = 0;
		velocity.mult(0.99f);
		acceleration.mult(0);
		setPoints();
		checkCollisions();
		followMouse();
		//checkCollisionsHorribly();
	}
	
	public void followMouse() {
		if (launcher.mousePressed) {
			if (position.x < launcher.mouseX)
				applyForce(new PVector(0.2f, 0));
			if (position.x > launcher.mouseX)
				applyForce(new PVector(-0.2f, 0));
		}
	}
	
	public void checkCollisionsHorribly() {
		for (Particle p : launcher.pHandler.getParticles()) {
			if (checkOverlap(p)) {
				p.applyForce(getCollisionForce(p).mult(mass));
				collisions ++;
			}
		}
	}
	
	public void checkCollisions() {
		int x = PApplet.floor(position.x / flowField.scale);
		int y = PApplet.floor(position.y / flowField.scale);
		
		// Checks particles at current position field square and surrounding squares
		for (int b = -2; b <= 1; b++) {
			for (int a = -2; a <= 1; a++) {
				int xa = (x + a) > (launcher.width / flowField.scale) ? flowField.scale : (x + a);
				int yb = (y + b) > (launcher.height / flowField.scale) ? flowField.scale : (y + b);
				if (xa < 0)
					xa = 0;
				if (yb < 0)
					yb = 0;
				ArrayList<Particle> listAt = launcher.pHandler.getActiveFieldAt(flowField.getIndex(xa, yb));
				for (Particle p : listAt) {
					if (checkOverlap(p)) {
						p.applyForce(getCollisionForce(p));
						collisions ++;
					}
					if (MainApp.drawCollisionLines) {
						launcher.noFill();
						launcher.strokeWeight(2);
						launcher.stroke(255, 100, 255);
						if (getDistance(p.position, position) < 100)
							launcher.line(p.position.x, p.position.y, position.x, position.y);
					}
				}
			}
		}
	}
}
