package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import main.Constants;
import main.Runner;
import sprite.Animation;
import tiles.Tile;
import weapon.Weapon;

public class Ripper extends Enemy {
	
	Animation animation;
	
	AffineTransform at;

	Area frontBox;
	
	private boolean isLeft, turning;
	int size, turnWait;
	double dir, speed = 3;
	
	
	public Ripper(double x, double y, Runner in) {
		
		super(x, y);
		
		health = 862;
		
		instance = in;
		
		animation = Constants.ripperL;
		
		w = animation.getSprite().getWidth();
		h = animation.getSprite().getHeight();
		
		animation.start();
		
		dir = -0.75;
		size = 3;
		turnWait = 0;
		
		isLeft = true;
		turning = false;
		
		at = new AffineTransform();
		at.translate(x, y);
		at.scale(1, 1);
		hitBox = new Area(new Rectangle(0, 0, w, h));
		frontBox = new Area(new Rectangle(-3, h / 2 - 5, 5, 10));
		attackBox = new AttackBox(hitBox, 10);
	}
	
	public void draw(Graphics2D g) {
		
		at = new AffineTransform();
		at.translate(x, y);
		at.scale(size, size);
		g.transform(at);
		if (!isFrozen) {
			g.drawImage(animation.getSprite(), 0, 0, null);
			animation.update();
		} else {
			g.drawImage(animation.getSprite(), 0, 0, null);
			g.setColor(new Color(0, 210, 255, 100));
			g.fillRoundRect(0, 0, w, h, 3, 3);
		}
		
		if (Constants.SHOWHITBOXES) {
			at = new AffineTransform();
			at.translate(0, 0);
			g.transform(at);
			g.setColor(new Color(255, 255, 255, 175));
			g.fill(hitBox);
			g.setColor(new Color(0, 255, 255, 175));
			g.fill(frontBox);
		}
		
		move();
		
	}
	
	public void move() {
		
		if (!turning)
			dx = dir * speed;
		else
			dx = 0;

		checkTink();
		checkIfFrozen();
		checkBeingHurt();
		health = 862;
		
		if (animation.equals(Constants.ripperTurnL) && animation.hasStopped()) {
			turning = false;
			animation = Constants.ripperL;
			animation.restart();
		}
		
		if (animation.equals(Constants.ripperTurnR) && animation.hasStopped()) {
			turning = false;
			animation = Constants.ripperR;
			animation.restart();
		}
		
		int frontTilesSize = instance.getRoom().getIntersectingTiles(frontBox).size();
		
		if (frontTilesSize > 0 && turnWait >= 5)
			turn();
		
		x += dx;
		
		if (turnWait < 10)
			turnWait++;
		
		updateHitBoxes();
	}
	
	public void turn() {
		turning = true;
		if (isLeft) {
			animation = Constants.ripperTurnR;
			isLeft = false;
		} else {
			animation = Constants.ripperTurnL;
			isLeft = true;
		}
		animation.restart();
		dir *= -1;
	}
	
	public void updateHitBoxes() {
		at = new AffineTransform();
		at.translate(x, y);
		at.scale(size, size);
		hitBox = new Area(new Rectangle(0, 0, w, h));
		if (isLeft)
			frontBox = new Area(new Rectangle(-3, h / 2 - 5, 5, 10));
		else
			frontBox = new Area(new Rectangle(w - 3, h / 2 - 5, 5, 10));
		
		frontBox.transform(at);
		hitBox.transform(at);
		
		if (isFrozen)
			attackBox = new AttackBox(new Area( new Rectangle(0, 0, 0, 0)), 0);
		else
			attackBox = new AttackBox(hitBox, 10);
	}
	
	public void checkTink() {
		ArrayList<Weapon> weapons = instance.getPlayer().getWeapons();
		
		for (Weapon w : weapons) {
			if (w.getWeaponBox().intersects(attackBox.getBounds2D()) && !w.getType().equals("ice")) {
				try {
					AudioInputStream stream = AudioSystem.getAudioInputStream(new File("Music/tink.wav"));
					Clip tink = AudioSystem.getClip();
					tink.open(stream);
					tink.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("woah");
			}
		}
		
	}
	
}
