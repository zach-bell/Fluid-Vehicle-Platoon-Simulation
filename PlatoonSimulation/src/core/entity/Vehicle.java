package core.entity;

import processing.core.PApplet;
import processing.core.PVector;

public class Vehicle {
	
	public PVector position;
	public PVector[] points;
	
	public float size = 3.25f;
	public float rotationSpeed = 0.02f;
	public float speed = 2;
	public float rotation = 0;
	public boolean leader = false;
	
	private PApplet launcher;
	
	public Vehicle(PApplet launcher, PVector center) {
		this.position = center;
		this.launcher = launcher;
		
		setPoints();
	}
	public Vehicle(PApplet launcher, float x, float y) {
		this.position = new PVector(x, y);
		this.launcher = launcher;
		
		setPoints();
	}
	public Vehicle(PApplet launcher, float x, float y, boolean leader) {
		this.position = new PVector(x, y);
		this.launcher = launcher;
		this.leader = leader;
		
		setPoints();
	}
	
	// creates the initial points for the shape to follow
	private void setPoints() {
		points = new PVector[] {
				new PVector(position.x, position.y + (10 * size)),
				new PVector(position.x + (5 * size), position.y - (5 * size)),
				new PVector(position.x, position.y - (2 * size)),
				new PVector(position.x - (5 * size), position.y - (5 * size))};
	}
	
	public void draw() {
		launcher.fill(255);
		launcher.beginShape();
		// Add verticies of the shape
		for (PVector point : points) {
			launcher.vertex(point.x, point.y);
		}
		launcher.rotate(PApplet.PI);
		launcher.endShape();
	}
}
