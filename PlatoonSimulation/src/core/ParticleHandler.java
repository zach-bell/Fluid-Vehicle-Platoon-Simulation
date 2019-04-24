package core;

import core.entity.Particle;
import processing.core.PApplet;
import processing.core.PVector;

public class ParticleHandler {
	
	private Particle[] particleList;
	private PApplet launcher;
	
	public ParticleHandler(PApplet launcher) {
		this.launcher = launcher;
		
		particleList = new Particle[500];
		initParticles();
	}
	
	public void initParticles() {
		for (int i = 0; i < particleList.length; i++) {
			particleList[i] = new Particle(launcher,
					new PVector(launcher.random(-(launcher.width / 2), (launcher.width / 2)),
					-launcher.random(launcher.height, launcher.height * 1.5f)));
		}
	}
	
	public void draw() {
		for (Particle p : particleList) {
			p.update();
			p.draw();
		}
	}
}
