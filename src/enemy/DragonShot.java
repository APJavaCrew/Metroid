package enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import entity.Entity;

public class DragonShot extends Enemy {

	private int dir;
	private double size = 15, origX;
	
	public DragonShot(double x, double y, int dir) {
		super(x, y);
		origX = x;
		dy = -10;
		this.dir = dir;
		hitBox = new Area( new Rectangle( (int) x, (int) y, (int) size, (int) size));
		attackBox = new AttackBox(hitBox, 20);
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(new Color(255, 100, 20));
		g.fillOval( (int) x, (int) y, (int) size, (int) size);
		
		dx = dir * 5.0;
		dy += .33;
		
		x += dx;
		y += dy;

		if (instance != null) {
			if (instance.getRoomBounds().intersects(hitBox.getBounds2D()))
				isAlive = false;
		}
		
		updateHitBoxes();
		
	}
	
	protected void updateHitBoxes() {
		hitBox = new Area( new Rectangle( (int) x, (int) y, (int) size, (int) size));
		attackBox.set(hitBox);
	}

}
