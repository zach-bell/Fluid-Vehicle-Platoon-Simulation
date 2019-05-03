package core;

import java.util.ArrayList;
import core.entity.Particle;
import core.entity.PhysicsObject;
import core.entity.Vehicle;
import processing.core.PVector;

public class PhysicsHandler {
	
	private Particle[] particleList;
	private Vehicle[] vehicles;
	private PVector[] targets;
	private Vehicle leader;
	private ArrayList<ArrayList<PhysicsObject>> activeListOverField;
	private MainApp launcher;
	private NoiseFlowField flowField;
	
	private int timeInterval = 20000;	// 20 seconds
	private int previousTime = 0;
	
	public PhysicsHandler(MainApp launcher, NoiseFlowField flowField) {
		this.launcher = launcher;
		this.flowField = flowField;
		
		activeListOverField = new ArrayList<ArrayList<PhysicsObject>>();
		
		initParticles();
		initFieldLists();
		initVehicles();
	}
	
	public void initParticles() {
		MainApp.println("Initializing particles...");
		particleList = new Particle[MainApp.particleCount];
		for (int i = 0; i < particleList.length; i++) {
			particleList[i] = new Particle(launcher, flowField, this,
					new PVector(launcher.random(launcher.width), launcher.random(launcher.height)));
		}
	}
	
	public void initFieldLists() {
		for (int i = 0; i < (flowField.cols * flowField.rows); i++) {
			activeListOverField.add(new ArrayList<PhysicsObject>());
		}
	}
	
	public void initVehicles() {
		MainApp.println("Initializing vehicles...");
		leader = new Vehicle(launcher, flowField, this,
				(launcher.width / 2), (launcher.height / 2), true);
		vehicles = new Vehicle[] {leader,
				new Vehicle(launcher, flowField, this,
						(launcher.width / 2), (launcher.height / 1.7f)),
				new Vehicle(launcher, flowField, this,
						(launcher.width / 2), (launcher.height / 1.47f)),
				new Vehicle(launcher, flowField, this,
						(launcher.width / 2), (launcher.height / 1.3f)),
		};
		
		// Initialize targets
		targets = new PVector[vehicles.length];
		for (int i = 0; i < targets.length; i++) {
			targets[i] = new PVector(launcher.random(0, launcher.width), (launcher.height / 2));
		}
	}
	
	public void draw() {
		if (!MainApp.platoonVehicles)
			randomizeTargets();
		
		for (Particle p : particleList) {
			p.update();
			p.draw();
		}
		/* Normally we would draw just Physics Object draw and update
		 * but we need to draw vehicles on top of the particles
		 */
		int vIndex = 0;
		for (Vehicle v : vehicles) {
			v.update();
			v.draw();
			
			if (!v.leader)
				if (MainApp.platoonVehicles)
					v.followTarget(leader.position);
				else
					v.followTarget(targets[vIndex]);
			vIndex ++;
		}
	}
	
	public void randomizeTargets() {
		int timePassed = launcher.millis() - previousTime;

		for (PVector vec : targets) {
			if (timePassed > timeInterval) {
				vec.set(launcher.random(0, launcher.width), (launcher.height / 2));
				previousTime = launcher.millis();
			}
			launcher.noFill();
			launcher.stroke(50, 255, 50);
			launcher.strokeWeight(1);
			launcher.ellipse(vec.x, vec.y, 4, 4);
		}
	}
	
	public float getAverageCollisions() {
		int sum = 0;
		for (Vehicle v : vehicles)
			sum += v.collisions;
		return sum / vehicles.length;
	}
	
	public void moveLeaderVehicle() {
		leader.followTarget(new PVector(launcher.mouseX, launcher.mouseY));
	}
	
	public Particle[] getParticles() {
		return particleList;
	}
	
	public ArrayList<PhysicsObject> getActiveFieldAt(int index) {
		return activeListOverField.get(index);
	}
	
	public void addObjectToListOverField(PhysicsObject p) {
		activeListOverField.get(p.flowFieldIndex).add(p);
	}
	
	public void removeObjectFromListOverField(PhysicsObject p, int index) {
		activeListOverField.get(p.flowFieldIndex).remove(p);
	}
}
