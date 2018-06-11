package enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;

import entity.Entity;

public class DragonShot extends Enemy {

	private int dir;
	
	public DragonShot(double x, double y, int dir) {
		super(x, y);
		this.dir = dir;
		hitBox = new Area( new Rectangle( (int) x, (int) y, 10, 10));
		attackBox = new AttackBox(hitBox, 20);
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.fillOval( (int) x, (int) y, 10, 10);
		
		x += dir / 2.0;
		y -= 1;
	
		System.out.println("boi");
		
	}
	
	protected void updateHitBoxes() {
		hitBox = new Area( new Rectangle( (int) x, (int) y, 10, 10));
		attackBox.set(hitBox);
	}

}
