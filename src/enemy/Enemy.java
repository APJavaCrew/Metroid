package enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import entity.Being;
import main.Runner;
import sprite.Animation;
import weapon.Beam;
import weapon.Weapon;

public class Enemy extends Being {
	
	Animation animation;
	
	protected AttackBox attackBox;
	protected double x, y; //position
	protected double dx, dy; //horiz/vert speed
	protected boolean isHurt = false, isFrozen = false;
	protected int hurtTimeout;
	Runner instance;
	
	public Enemy(double x, double y) {
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
	}
	
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
			if (beams != null) {
				for (Weapon b : beams) {
					if ( b.getWeaponBox().intersects(hitBox.getBounds2D()) ) {
						isHurt = true;
						health -= b.getWeaponBox().getDamage();
						hurtTimeout = 0;
						
						try {
							AudioInputStream stream = AudioSystem.getAudioInputStream(new File("Music/enemyHurt.wav"));
							Clip hurts = AudioSystem.getClip();
							hurts.open(stream);
							hurts.start();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						
						switch (b.getType()) {
							default:
								break;
							case "ice":
								isFrozen = true;
								break;
						}
					}
				}
			}
		}
		
		if (health <= 0)
			isAlive = false;
	}
	

}
