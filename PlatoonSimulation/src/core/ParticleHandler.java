package core;

import core.entity.Particle;
import processing.core.PApplet;
import processing.core.PVector;

public class ParticleHandler {
	
	private Particle[] particleList;
	private PApplet launcher;
	private NoiseFlowField flowField;
	
	public ParticleHandler(PApplet launcher, NoiseFlowField flowField) {
		this.launcher = launcher;
		this.flowField = flowField;
		
		particleList = new Particle[1000];
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
}
