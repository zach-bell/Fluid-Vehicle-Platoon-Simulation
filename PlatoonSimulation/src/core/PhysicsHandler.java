package core;

import java.util.ArrayList;

import core.entity.Particle;
import core.entity.PhysicsObject;
import core.entity.Vehicle;
import processing.core.PVector;

public class PhysicsHandler {
	
	private Particle[] particleList;
	private Vehicle[] vehicles;
	private ArrayList<ArrayList<PhysicsObject>> activeListOverField;
	private MainApp launcher;
	private NoiseFlowField flowField;
	
	public PhysicsHandler(MainApp launcher, NoiseFlowField flowField) {
		this.launcher = launcher;
		this.flowField = flowField;
		
		particleList = new Particle[MainApp.particleCount];
		activeListOverField = new ArrayList<ArrayList<PhysicsObject>>();
		
		initParticles();
		initFieldLists();
		initVehicles();
	}
	
	public void initParticles() {
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
		vehicles = new Vehicle[] {
				new Vehicle(launcher, flowField, this, (launcher.width / 2), (launcher.height / 2), true)
		};
	}
	
	public void draw() {
		for (Particle p : particleList) {
			p.update();
			p.draw();
		}
		/* Normally we would draw just Physics Object draw and update
		 * but we need to draw vehicles on top of the particles
		 */
		for (Vehicle v : vehicles) {
			v.update();
			v.draw();
		}
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
