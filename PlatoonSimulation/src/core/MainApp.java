package core;

import core.entity.Vehicle;
import processing.core.PApplet;

public class MainApp extends PApplet {
	
	// Main method. That makes this the launcher class
	public static void main(String args[]) {
		PApplet.main("core.MainApp");
	}
	
	// ------------------------------------
	// All variables after for organization
	// ------------------------------------
	
	public Vehicle[] vehicles;
	public ParticleHandler pHandler;
	public float screenY = 0;
	public float screenSpeed = 1.75f;
	
	// To setup the program specifically
	public void settings() {
		size(1240, 720);
	}
	
	// Initializers should go here
	public void setup() {
		pHandler = new ParticleHandler(this);
		
		// Create a new vehicle
		vehicles = new Vehicle[] {
			new Vehicle(this, 0, 0, true)	
		};
	}
	
	// the draw loop
	public void draw() {
		background(25);
		
		text("Screen pos: " + screenY, 10, 10);
		translate(width / 2, height / 2);
		
		// Draw particles first
		pHandler.draw();
		
		// For each vehicle
		fill(200);
		for (Vehicle v : vehicles) {
			v.draw();
		}
		screenY += screenSpeed;
	}
}
