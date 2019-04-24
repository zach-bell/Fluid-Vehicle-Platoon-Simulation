package core.entity;

import processing.core.PApplet;
import processing.core.PVector;

public class Vehicle {
	
	public PVector center;
	public PVector[] points;
	
	public float size = 3.25f;
	public float rotationSpeed = 0.02f;
	public float speed = 2;
	public float rotation = 0;
	public boolean leader = false;
	
	private PApplet launcher;
	
	public Vehicle(PApplet launcher, PVector center) {
		this.center = center;
		this.launcher = launcher;
		
		setPoints();
	}
	public Vehicle(PApplet launcher, float x, float y) {
		this.center = new PVector(x, y);
		this.launcher = launcher;
		
		setPoints();
	}
	public Vehicle(PApplet launcher, float x, float y, boolean leader) {
		this.center = new PVector(x, y);
		this.launcher = launcher;
		this.leader = leader;
		
		setPoints();
	}
	
	// creates the initial points for the shape to follow
	private void setPoints() {
		points = new PVector[] {
				new PVector(center.x, center.y + (10 * size)),
				new PVector(center.x + (5 * size), center.y - (5 * size)),
				new PVector(center.x, center.y - (2 * size)),
				new PVector(center.x - (5 * size), center.y - (5 * size))};
	}
	
	public void draw() {
		launcher.beginShape();
		
		updatePos();
		
		// Add verticies of the shape
		for (PVector point : points) {
			launcher.vertex(point.x, point.y);
		}
		launcher.translate((rotation * -200) + (launcher.displayWidth / 3),0);
		launcher.rotate(rotation);
		launcher.endShape();
	}
	
	private void updatePos() {
		rotation = (float) ((Math.sin(center.y) * 0.5f) + Math.PI);
		center.y -= rotationSpeed;
	}
}
