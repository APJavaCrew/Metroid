package weapon;

import java.awt.Graphics;
import java.awt.geom.Area;

import entity.Entity;

public class WeaponBox extends Entity {

	double damage;
	
	public WeaponBox(Area box, double damage) {
		this.damage = damage;
		hitBox = box;
	}
	
	@Override
	public void draw(Graphics g) {
		return;
	}

}
