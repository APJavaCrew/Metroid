package beam;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;

import entity.Entity;
import main.Runner;

public class Beam extends Entity {
	
	private boolean alive;
	private double angle, velModifier, b;
	private Runner instance;
	
	public Beam(double deg, double velModifier, double x, double y) {
		alive = true;
		angle = Math.toRadians(deg);
		this.velModifier = velModifier;
		b = y;
		this.y = y;
		this.x = x;
		w = 20;
		h = 20;
		
		hitBox = new Area(new Rectangle((int) x, (int) y, w, h));
		
	}

	@Override
	public void draw(Graphics g) {		
		move();
		
		g.setColor(new Color(255, 200, 0));
		g.fillOval((int) x, (int) y, w, h);
		
	}
	
	private void updateHitBox() {
		hitBox = new Area(new Rectangle((int) x, (int) y, w, h));
	}
	
	public void updateInstance(Runner in) {
		instance = in;
	}
	
	private void move() {
		y -= Math.tan(angle);
		x -= 2.0 * velModifier;
		
		updateHitBox();
		
		if (instance.getRoomBounds().intersects(hitBox.getBounds2D()))
			alive = false;
	}
	
	public boolean isAlive() {
		return alive;
	}

}
