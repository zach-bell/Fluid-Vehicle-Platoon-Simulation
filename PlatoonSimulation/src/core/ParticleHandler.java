package core;

import java.util.ArrayList;

import core.entity.Particle;
import processing.core.PApplet;
import processing.core.PVector;

public class ParticleHandler {
	
	private Particle[] particleList;
	private ArrayList<ArrayList<Particle>> particleListOverField;
	private PApplet launcher;
	private NoiseFlowField flowField;
	
	public ParticleHandler(PApplet launcher, NoiseFlowField flowField) {
		this.launcher = launcher;
		this.flowField = flowField;
		
		particleList = new Particle[MainApp.particleCount];
		particleListOverField = new ArrayList<ArrayList<Particle>>();
		initParticles();
		initFieldLists();
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
	
	public void draw() {
		updateOverField();
		for (Particle p : particleList) {
			p.update();
			p.draw();
		}
	}
	
	public void updateOverField() {
// This was the old way, keep to learn from
//		for (ArrayList<Particle> aPlist : particleListOverField) {
//			aPlist.clear();
//		}
//		for (Particle p : particleList) {
//			int x = PApplet.floor(p.position.x / flowField.scale);
//			int y = PApplet.floor(p.position.y / flowField.scale);
//			int index = x + y * flowField.cols;
//			index = PApplet.constrain(index, 1, flowField.cols * flowField.rows) - 1;
//			
//			particleListOverField.get(index).add(p);
//			p.activeGroup = new ArrayList<Particle>(particleListOverField.get(index));
//		}
		if (MainApp.drawCollisionLines) {
			for (Particle p : particleList) {
				p.drawCollisionLines(particleListOverField.get(p.flowFieldIndex));
			}
		}
	}
	
	public void addParticleToListOverField(Particle p) {
		particleListOverField.get(p.flowFieldIndex).add(p);
		p.activeGroup = particleListOverField.get(p.flowFieldIndex);
	}
	
	public void removeParticleFromListOverField(Particle p, int index) {
		particleListOverField.get(p.flowFieldIndex).remove(p);
	}
}
