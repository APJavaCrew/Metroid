package enemy;

import java.awt.geom.Area;

import main.Runner;
import player.Player;

public class AttackBox {
	
	Runner instance;
	
	Area hitBox;
	double damage;

	public AttackBox(Area box, double damage) {
		this.damage = damage;
		hitBox = box;
	}
	
	public void updateInstance(Runner in) {
		instance = in;
	}
	
}
