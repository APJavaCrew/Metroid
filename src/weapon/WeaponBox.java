package weapon;

import java.awt.Graphics2D;
import java.awt.geom.Area;

import entity.Entity;

public class WeaponBox extends Entity {

	double damage;
	
	public WeaponBox(Area box, double damage) {
		this.damage = damage;
		hitBox = box;
	}
	
	@Override
	public void draw(Graphics2D g) {
		return;
	}

}
