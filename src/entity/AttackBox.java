package entity;

import java.awt.Graphics;
import java.awt.geom.Area;

public class AttackBox extends Entity {
	protected Area attackbox;

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	public  boolean isCollidedWithPlayer(Player player){
		
			return player.hitbox.contains(this.attackbox.getBounds2D());
		
	}

}
