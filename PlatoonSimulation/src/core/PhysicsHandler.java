package core;

import java.util.ArrayList;

import core.entity.Particle;
import core.entity.Vehicle;
import processing.core.PVector;

public class PhysicsHandler {
	
	private Particle[] particleList;
	private Vehicle[] vehicles;
	private ArrayList<ArrayList<Particle>> particleListOverField;
	private MainApp launcher;
	private NoiseFlowField flowField;
	
	public PhysicsHandler(MainApp launcher, NoiseFlowField flowField) {
		this.launcher = launcher;
		this.flowField = flowField;
		
		particleList = new Particle[MainApp.particleCount];
		particleListOverField = new ArrayList<ArrayList<Particle>>();
		
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
			particleListOverField.add(new ArrayList<Particle>());
		}
	}
	
	public void initVehicles() {
		vehicles = new Vehicle[] {
				new Vehicle(launcher, (launcher.width / 2), (launcher.height / 2), true)
		};
	}
	
	public void draw() {
		// We want to keep these lists separate until the new method is designed
		if (MainApp.drawCollisionLines) {
			for (Particle p : particleList) {
				p.drawCollisionLines(particleListOverField.get(p.flowFieldIndex));
			}
		}
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
	
	public ArrayList<Particle> getActiveFieldAt(int index) {
		return particleListOverField.get(index);
	}
	
	public void addParticleToListOverField(Particle p) {
		particleListOverField.get(p.flowFieldIndex).add(p);
		p.activeGroup = particleListOverField.get(p.flowFieldIndex);
	}
	
	public void removeParticleFromListOverField(Particle p, int index) {
		particleListOverField.get(p.flowFieldIndex).remove(p);
	}
}
