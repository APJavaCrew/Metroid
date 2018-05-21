package weapon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import entity.Entity;
import main.Runner;

public class Beam extends Entity {
	
	private boolean alive;
	private double angle, velModifier, b, damage, size, w, h;
	private Runner instance;
	private WeaponBox weaponBox;
	
	Graphics2D g2d;
	
	public Beam(double deg, double velModifier, double size, double x, double y) {
		alive = true;
		angle = Math.toRadians(deg);
		this.velModifier = velModifier;
		b = y;
		this.y = y;
		this.x = x;
		w = size;
		h = size - 5.0;
		this.size = size;
		damage = Math.pow(2, size);
		
		dx = 0;
		dy = 0;
		
		hitBox = new Area(new Rectangle((int) x, (int) y, (int) w, (int) h));
		weaponBox = new WeaponBox(new Area(hitBox.getBounds2D()), damage);
	}

	@Override
	public void draw(Graphics g) {
		g2d = (Graphics2D) g;
		
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.rotate(angle);
		
		g2d.setTransform(at);
		g2d.setColor(new Color(255, 200, 0));
		g2d.fillOval(0, 0, (int) w, (int) h);

		move();
		
	}
	
	private void updateHitBox() {
		hitBox = new Area(new Rectangle((int) x, (int) y, (int) w, (int) h));
	}
	
	public void updateInstance(Runner in) {
		instance = in;
	}
	
	private void move() {
		if (angle == Math.PI / 2.0) {
			dy = -3 * velModifier;
			dx = 0;
		} else {
			dy = Math.tan(angle) * velModifier;
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
