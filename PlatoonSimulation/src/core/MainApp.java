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
	
	public ParticleHandler pHandler;
	public NoiseFlowField noiseField;
	public Vehicle[] vehicles;
	public float screenY = 0;
	public float screenSpeed = 1.75f;
	
	// To setup the program specifically
	public void settings() {
		size(1240, 720);
	}
	
	// Initializers should go here
	public void setup() {
		noiseField = new NoiseFlowField(this);
		pHandler = new ParticleHandler(this, noiseField);
		
		// Create a new vehicle
		vehicles = new Vehicle[] {
			new Vehicle(this, width / 2, height / 2, true)	
		};
	}
	
	// the draw loop
	public void draw() {
		background(25);
		noiseField.draw();
		
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
