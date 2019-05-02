package core;

import processing.core.PApplet;
import processing.core.PVector;

public class NoiseFlowField {
	
	public PApplet launcher;
	public int scale = 30;				// Scale of the field
	public float inc = 0.143f;			// Scale of the noise
	public float translation = 0.0125f;	// Speed of the noise movement in 3D
	public float magnitude = 50;		// Strength of the field
	
	public float zoff = 0;
	public int cols, rows;
	public PVector[] flowField;
	
	public NoiseFlowField(PApplet launcher) {
		this.launcher = launcher;
		
		cols = PApplet.floor(launcher.width / scale);
		rows = PApplet.floor(launcher.height / scale);
		flowField = new PVector[cols * rows];
	}
	
	public void draw() {
		float yoff = 0;
		for (int y = 0; y < rows; y++) {
			float xoff = 0;
			for (int x = 0; x < cols; x++) {
				int index = x + y * cols;
				float angle = launcher.noise(xoff, yoff, zoff) * (PApplet.TWO_PI - PApplet.PI);
				
				PVector v = PVector.fromAngle(angle);
				flowField[index] = v.setMag(magnitude);
				
				xoff += inc;
				if (MainApp.drawFieldForces) {
					launcher.stroke(0, 200, 200);
					launcher.pushMatrix();
					launcher.translate(x * scale, y * scale);
					launcher.rotate(v.heading());
					launcher.line(0, 0, scale, 0);
					launcher.popMatrix();
				}
				if (MainApp.drawFieldBounds) {
					launcher.stroke(0, 50, 200);
					launcher.noFill();
					launcher.rect(x * scale, y * scale, scale, scale);
				}
			}
			yoff += inc;
		}
		zoff += translation;
	}
	
	public PVector flowFieldForceAtIndex(int index) {
		return flowField[index];
	}
	
	public int getIndex(int x, int y) {
		return x + y * cols;
	}
	
	public int getIndex(PVector position) {
		int x = PApplet.floor(position.x / scale);
		int y = PApplet.floor(position.y / scale);
		return x + y * cols;
	}
}
