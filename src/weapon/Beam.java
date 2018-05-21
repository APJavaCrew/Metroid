package weapon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;

import entity.Entity;
import main.Runner;

public class Beam extends Entity {
	
	private boolean alive;
	private double angle, velModifier, b, damage;
	private Runner instance;
	private WeaponBox weaponBox;
	
	public Beam(double deg, double velModifier, double damage, double x, double y) {
		alive = true;
		angle = Math.toRadians(deg);
		this.velModifier = velModifier;
		b = y;
		this.y = y;
		this.x = x;
		w = 20;
		h = 20;
		this.damage = damage;
		
		dx = 0;
		dy = 0;
		
		hitBox = new Area(new Rectangle((int) x, (int) y, w, h));
		weaponBox = new WeaponBox(new Area(hitBox.getBounds2D()), damage);
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
		if (angle == Math.PI / 2.0) {
			dy = -3 * velModifier;
			dx = 0;
		} else {
			dy = Math.tan(angle);
			dx = 2.0 * velModifier;
		}
		
		y += dy;
		x += dx;
		
		updateHitBox();
		
		if (instance.getRoomBounds().intersects(hitBox.getBounds2D()))
			alive = false;
	}
	
	public boolean isAlive() {
		return alive;
	}

}
