package core;

import core.entity.Vehicle;
import processing.core.PApplet;

public class MainApp extends PApplet {
	
	public static boolean drawCollisionLines = false;
	public static boolean drawFieldForces = false;
	public static boolean drawFieldBounds = false;
	public static int particleCount = 1500;
	
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
		
		drawUI();
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
	
	public void drawUI() {
		fill(255);
		textSize(22);
		text("FPS: " + floor(frameRate), width - 100, 20);
	}
}
