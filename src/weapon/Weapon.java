package weapon;

import java.awt.Graphics2D;

import entity.Entity;
import main.Runner;

public abstract class Weapon extends Entity {

	protected WeaponBox weaponBox;
	protected boolean isRemoved;
	protected Runner instance;
	protected String type;
	
	public abstract void draw(Graphics2D g);
	public WeaponBox getWeaponBox() {return weaponBox;}
	
	public void updateInstance(Runner in) { instance = in; }
	
	public boolean isRemoved() {
		return isRemoved;
	}
	
	public String getType() {
		return type;
	}

}
