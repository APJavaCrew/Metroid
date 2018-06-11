package enemy;

import java.awt.Graphics2D;

import entity.Entity;

public class DragonShot extends Entity {

	private int dir;
	
	public DragonShot(double x, double y, int dir) {
		super(x, y);
		this.dir = dir;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.fillOval( (int) x, (int) y, 10, 10);
		
		x += dir / 2.0;
		y = Math.pow(x, 2.0);
		
	}

}
