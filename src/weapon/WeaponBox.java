package weapon;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;

import enemy.Enemy;
import entity.Entity;

public class WeaponBox extends Area {

	double damage;
	
	public WeaponBox(Area box, double damage) {
		this.damage = damage;
		add(box);
	}
	
	public void set(Area box) {
		subtract(this);
		add(box);
	}
	
	public void set(Rectangle box) {
		subtract(this);
		add( new Area(box) );
	}
	
	public boolean isIntersectingEnemy(Enemy e) {
		return intersects(e.getHitBox().getBounds2D());
	}
	
	public double getDamage() {
		return damage;
	}

}
