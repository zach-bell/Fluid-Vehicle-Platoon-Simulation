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
	public static boolean platoonVehicles = true;
	
	public static float particleMass = 0.02f;
	public static float vehicleMass = 1000;
	public static int particleCount = 1500;
	public static int flowFieldScale = 30;
	public static PVector globalForce;
	
	// Main method. That makes this the launcher class
	public static void main(String args[]) {
		println("Launching Vehicle platooning program demonstration...");
		
		try {
			if (args[0] != null) {
				particleCount = Integer.parseInt(args[0]);
			}
			if (args[1] != null) {
				particleMass = (float) Double.parseDouble(args[1]);
			}
			if (args[2] != null) {
				vehicleMass = (float) Double.parseDouble(args[2]);
			}
			if (args[3] != null) {
				flowFieldScale = Integer.parseInt(args[3]);
			}
		} catch (NumberFormatException e) {
			println("So those numbers seemed to not actually be numbers.");
			printArgumentOptions();
		} catch (Exception e) {
			println("\nAn error occured, you probably didn't put any arguments.");
			printArgumentOptions();
		}
		PApplet.main("core.MainApp");
	}
	
	public static void printArgumentOptions() {
		println("\nLooks like you're trying to put in some arguments there...");
		println("------------------------------------------------------------");
		println("Usage (all arguments are optional):");
		println("java -jar PlatoonSimulation.jar [ParticleCount] [ParticleMass] [VehicleMass]"
				+ " [FlowFieldScale]\n");
		println("[Particle Count]\nSets the count of particles to run in the simulation.\nPlease"
				+ " be careful with how many you enter as over 5000 gets really dicey."
				+ "\nDefault is 1500\n");
		println("[Particle Mass]\nSets the mass of particles in the simulation."
				+ "\nDefault is 0.02\n");
		println("[Vehicle Mass]\nSets the mass of the vehicles in the simulation."
				+ "\nDefault is 1000\n");
		println("[Flow Field Scale]\nSets the size of the field scale.\nIt is dangerous to lower"
				+ " this number above the width of the screen."
				+ "\nDefault is 30");
		println("------------------------------------------------------------");
		println("Running with default values instead!\n");
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
		rect((width / 2) - 70, 2, 170, 22);
		fill(255);
		textSize(22);
		text("FPS: " + floor(frameRate), width - 100, 20);
		text("Particles: " + particleCount, (width / 2) - 70, 20);
		
		
		drawButtons();
		
		noStroke();
		fill(25);
		rect(6, 2, 260, 22);
		fill((int) map(pHandler.getAverageCollisions(), 0, 300, 150, 255),
				(int) map(300 - pHandler.getAverageCollisions(), 0, 300, 150, 255), 150);
		textSize(22);
		text("Average Collisions: " + floor(pHandler.getAverageCollisions()), 10, 20);
	}
	
	public void drawButtons() {
		textSize(12);
		// toggle platooning button
		fill(platoonVehicles ? buttonActiveColor : buttonInactiveColor);
		rect(10, 30, 90, 22);
		fill(platoonVehicles ? 10 : 255);
		text("TogglePlatoon", 14, 46);
		
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
		if (mouseX < 90 & mouseX > 10) {
			if (mouseY < 52 & mouseY > 30) {
				platoonVehicles = !platoonVehicles;
			}
		}
			
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
