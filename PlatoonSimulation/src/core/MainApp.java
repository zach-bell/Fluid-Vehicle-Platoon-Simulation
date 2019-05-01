package core;

import processing.core.PApplet;
import processing.core.PVector;

public class MainApp extends PApplet {
	
	public static boolean drawCollisionLines = false;
	public static boolean drawFieldForces = false;
	public static boolean drawFieldBounds = false;
	public static boolean drawParticles = true;
	public static boolean drawVehicles = true;
	public static int particleCount = 1000;
	public static PVector globalForce;
	
	// Main method. That makes this the launcher class
	public static void main(String args[]) {
		PApplet.main("core.MainApp");
	}
	
	// ------------------------------------
	// All variables after for organization
	// ------------------------------------
	
	public PhysicsHandler pHandler;
	public NoiseFlowField noiseField;
	
	// To setup the program specifically
	public void settings() {
		size(1240, 720);
	}
	
	// Initializers should go here
	public void setup() {
		globalForce = new PVector(0, 0.5f);
		
		noiseField = new NoiseFlowField(this);
		pHandler = new PhysicsHandler(this, noiseField);
	}
	
	// the draw loop
	public void draw() {
		background(25);
		update();
		noiseField.draw();
		pHandler.draw();
		drawUI();
	}
	
	public void update() {
		
	}
	
	public NoiseFlowField getNoiseField() {
		return noiseField;
	}
	
	public void drawUI() {
		noStroke();
		fill(25);
		rect(width - 100, 0, 80, 22);
		fill(255);
		textSize(22);
		text("FPS: " + floor(frameRate), width - 100, 20);
	}
}
