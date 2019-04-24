package core.entity;

import processing.core.PApplet;
import processing.core.PVector;

public class Particle {
	
	private PApplet launcher;
	private PVector position;
	private float size = 10;
	private float verticalSpeed = 3;
	
	public Particle(PApplet launcher, PVector position) {
		this.launcher = launcher;
		this.position = position;
	}
	
	public void draw() {
		launcher.ellipse(position.x, position.y, size, size);
	}
	
	public void update() {
		position.y += verticalSpeed;
	}
	
	public void checkCollision() {
		
	}
}
