package enemy;

import java.awt.Rectangle;
import java.awt.geom.Area;

import entity.Being;
import main.Runner;
import weapon.Beam;

public class Enemy extends Being {
	
	protected AttackBox attackBox;
	protected double x, y; //position
	protected double dx, dy; //horiz/vert speed 
	Runner instance;

	@Override
	public void move() {
		y=0;
		x=0;
		dx=0;
		dy=0;
		
	}
	
	protected void updateHitBoxes() {
		Rectangle hitBoxRect = new Rectangle((int) x, (int) y + 5, w, h - 10);
		hitBox = new Area(hitBoxRect);
		Rectangle landBoxRect = new Rectangle((int) (x + 2), (int) (h + y - 5), w - 4, 5);
		landBox = new Area(landBoxRect);
		Rectangle topBoxRect = new Rectangle((int) (x + 2), (int) y, w - 4, 5);
		topBox = new Area(topBoxRect);
		attackBox.set(hitBox);
	}
	
	public void updateInstance(Runner in) {
		instance = in;
	}

	@Override
	public void checkCollision() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	

}
