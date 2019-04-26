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
		
		particleList = new Particle[750];
		particleListOverField = new ArrayList<ArrayList<Particle>>(flowField.cols * flowField.rows);
		initParticles();
	}
	
	public void initParticles() {
		for (int i = 0; i < particleList.length; i++) {
			particleList[i] = new Particle(launcher, flowField,
					new PVector(launcher.random(launcher.width), launcher.random(launcher.height)));
		}
	}
	
	public void draw() {
		for (Particle p : particleList) {
			p.update();
			p.draw();
		}
	}
	
	public void updateOverField() {
		for (ArrayList<Particle> aPlist : particleListOverField) {
			aPlist.clear();
		}
		for (Particle p : particleList) {
			int x = PApplet.floor(p.position.x / flowField.scale);
			int y = PApplet.floor(p.position.y / flowField.scale);
			int index = x + y * flowField.cols;
			index = PApplet.constrain(index, 1, flowField.cols * flowField.rows) - 1;
			particleListOverField.get(index).add(p);
		}
	}
}
