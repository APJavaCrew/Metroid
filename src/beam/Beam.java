package beam;

import java.awt.Color;
import java.awt.Graphics;

import entity.Entity;

public class Beam extends Entity {
	
	private boolean alive;
	private double angle, velModifier, b;
	
	public Beam(double deg, double velModifier, double y0) {
		alive = true;
		angle = Math.toRadians(deg);
		this.velModifier = velModifier;
		b = y0;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(255, 200, 0));
		g.fillOval((int) x, (int) y, w, h);
	}
	
	public void move() {
		x += 0.1 * velModifier;
		y = Math.tan(angle) * x + b;
	}
	
	public boolean isAlive() {
		return alive;
	}

}
