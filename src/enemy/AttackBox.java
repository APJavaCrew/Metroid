package enemy;

import java.awt.Rectangle;
import java.awt.geom.Area;

import main.Runner;
import player.Player;

public class AttackBox extends Area {
	
	Runner instance;
	
	double damage;

	public AttackBox(Area box, double damage) {
		this.damage = damage;
		add(box);
	}
	
	public void updateInstance(Runner in) {
		instance = in;
	}
	
	public void set(Area area) {
		subtract(this);
		add(area);
	}
	
	public void set(Rectangle rec) {
		subtract(this);
		add( new Area(rec) );
	}
	
	public boolean isIntersectingPlayer() {
		return instance.getPlayer().getHitBox().intersects(this.getBounds2D());
	}

	public double getDamage() {
		return damage;
	}
}
