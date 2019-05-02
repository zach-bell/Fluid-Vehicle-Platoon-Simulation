package core;

import processing.core.PApplet;
import processing.core.PVector;

public class MainApp extends PApplet {
	
	public static boolean drawCollisionLines = false;
	public static boolean drawFieldForces = false;
	public static boolean drawFieldBounds = false;
	public static boolean drawParticles = false;
	public static boolean drawVehicles = true;
	public static boolean drawPointFlow = true;
	
	public static int particleCount = 500;
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
	private int buttonActiveColor = 0;
	private int buttonInactiveColor = 0;
	
	// To setup the program specifically
	public void settings() {
		size(1240, 720);
	}
	
	// Initializers should go here
	public void setup() {
		surface.setTitle("Vehicle Platooning in a physics environment");
		globalForce = new PVector(0, 0.25f);
		
		buttonActiveColor = color(100, 255, 100);
		buttonInactiveColor = color(255, 100, 100);
		
		noiseField = new NoiseFlowField(this);
		pHandler = new PhysicsHandler(this, noiseField);
	}
	
	// the draw loop
	public void draw() {
		background(25);
		noiseField.draw();
		pHandler.draw();
		drawUI();
	}
	
	public NoiseFlowField getNoiseField() {
		return noiseField;
	}
	
	public void drawUI() {
		// Draw fps counter
		noStroke();
		fill(25);
		rect(width - 100, 0, 80, 22);
		fill(255);
		textSize(22);
		text("FPS: " + floor(frameRate), width - 100, 20);
		
		textSize(12);
		// Draw field bounds button
		fill(drawFieldBounds ? buttonActiveColor : buttonInactiveColor);
		rect(width - 100, 30, 80, 22);
		fill(drawFieldBounds ? 10 : 255);
		text("FieldBounds", width - 94, 48);
		
		// Draw field forces button
		fill(drawFieldForces ? buttonActiveColor : buttonInactiveColor);
		rect(width - 100, 58, 80, 22);
		fill(drawFieldForces ? 10 : 255);
		text("FieldForces", width - 94, 74);
		
		// Draw collision lines button
		fill(drawCollisionLines ? buttonActiveColor : buttonInactiveColor);
		rect(width - 100, 86, 80, 22);
		fill(drawCollisionLines ? 10 : 255);
		text("Collisions", width - 94, 102);
		
		// Draw particles button
		fill(drawParticles ? buttonActiveColor : buttonInactiveColor);
		rect(width - 100, 114, 80, 22);
		fill(drawParticles ? 10 : 255);
		text("Particles", width - 94, 130);
		
		// Draw vehicles button
		fill(drawVehicles ? buttonActiveColor : buttonInactiveColor);
		rect(width - 100, 142, 80, 22);
		fill(drawVehicles ? 10 : 255);
		text("Vehicles", width - 94, 158);
		
		// Draw point flow button
		fill(drawPointFlow ? buttonActiveColor : buttonInactiveColor);
		rect(width - 100, 168, 80, 22);
		fill(drawPointFlow ? 10 : 255);
		text("PointFlow", width - 94, 184);
	}
	
	public void mousePressed() {
		if (mouseX > width - 100) {
			// drawfieldbounds button
			if (mouseY > 30 & mouseY < 52) {
				drawFieldBounds = !drawFieldBounds;
			}
			// drawFieldForces button
			if (mouseY > 58 & mouseY < 80) {
				drawFieldForces = !drawFieldForces;
			}
			// drawCollisionLines button
			if (mouseY > 86 & mouseY < 108) {
				drawCollisionLines = !drawCollisionLines;
			}
			// drawParticles button
			if (mouseY > 114 & mouseY < 136) {
				drawParticles = !drawParticles;
			}
			// drawVehicles button
			if (mouseY > 142 & mouseY < 164) {
				drawVehicles = !drawVehicles;
			}
			// drawPointFlow button
			if (mouseY > 168 & mouseY < 190) {
				drawPointFlow = !drawPointFlow;
			}
		}
	}
	
	public void mouseDragged() {
		if (mouseX < width - 100 & mouseY > 190) {
			// move leader vehicle
			pHandler.moveLeaderVehicle();
		}
	}
}
