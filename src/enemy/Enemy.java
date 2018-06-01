package enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;

import entity.Being;
import main.Runner;
import weapon.Beam;
import weapon.Weapon;

public class Enemy extends Being {
	
	protected AttackBox attackBox;
	protected double x, y; //position
	protected double dx, dy; //horiz/vert speed
	protected boolean isHurt = false;
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

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}
	
	public AttackBox getAttackBox() {
		return attackBox;
	}
	
	public void checkBeingHurt() {
		if (instance != null) {
			ArrayList<Weapon> beams = instance.getPlayer().getWeapons();
			for (Weapon b : beams) {
				if ( b.getWeaponBox().intersects(hitBox.getBounds2D()) ) {
					isHurt = true;
					switch (b.getType()) {
						default:
							break;
						case "power":
							break;
					}
				}
			}
		}
	}
	

}
