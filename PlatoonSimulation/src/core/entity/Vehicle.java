package core.entity;

import core.MainApp;
import core.NoiseFlowField;
import core.PhysicsHandler;
import processing.core.PApplet;
import processing.core.PVector;

public class Vehicle extends PhysicsObject {
	
	public PVector[] points;
	
	public float size = 3.25f;
	public float movementSpeed = 0.05f;
	public boolean leader = false;
	public float initialY = 0;
	public int timeInterval = 5000;
	public int previousTime = 0;
	
	public Vehicle(MainApp launcher, NoiseFlowField flowField, PhysicsHandler handler, float x, float y) {
		this.position = new PVector(x, y);
		this.launcher = launcher;
		this.flowField = flowField;
		this.handler = handler;
		
		init();
	}
	
	public Vehicle(MainApp launcher, NoiseFlowField flowField, PhysicsHandler handler, float x, float y, boolean leader) {
		this.position = new PVector(x, y);
		this.launcher = launcher;
		this.leader = leader;
		this.flowField = flowField;
		this.handler = handler;
		
		init();
	}
	
	private void init() {
		initialY = position.y;
		followNoiseField = false;
		radius = 20;
		mass = 100;
		maxSpeed = 4;
		color = launcher.color(255, 100, 255);
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
			
			// Text for collisions
			launcher.noStroke();
			launcher.fill(220);
			launcher.textSize(10);
			launcher.text("" + collisions, position.x, position.y);
		}
	}
	
	public void update() {
		int timePassed = launcher.millis() - previousTime;
		if (timePassed > timeInterval) {
			collisions = 0;
			previousTime = launcher.millis();
		}
		
		checkCollisions();
		
		velocity.add(acceleration);
		velocity.limit(maxSpeed);
		position.add(velocity);
		if (position.x > launcher.width)
			position.x = launcher.width;
		if (position.x < 0)
			position.x = 0;
		if (position.y > (initialY) + 2)
			position.y = initialY;
		if (position.y < initialY - 2)
			position.y = initialY;
		velocity.mult(0.99f);
		acceleration.mult(0);
		
		setPoints();
	}
	
	public void followTarget(PVector target) {
		if (position.x < target.x - 5)
			applyForce(new PVector(movementSpeed, 0));
		if (position.x > target.x + 5)
			applyForce(new PVector(-movementSpeed, 0));
	}
}
