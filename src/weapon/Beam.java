package weapon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

import enemy.AttackBox;
import entity.Entity;
import main.Runner;

public class Beam extends Weapon {
	
	private double angle, velModifier, b, damage, size, w, h;
	Color color;
	
	public Beam(String type, double deg, double velModifier, double size, double x, double y) {
		isRemoved = false;
		angle = Math.toRadians(deg);
		this.velModifier = velModifier;
		b = y;
		this.y = y;
		this.x = x;
		w = size + 5.0;
		h = size;
		this.size = size;
		damage = 0.555555555555556 * Math.pow(1.116123174, size);
		this.type = type;
		
		AudioInputStream stream = null;
		
		switch (type) {
			default:
				type = "power";
			case "power":
				color = new Color(255, 200, 0);
				try {
					stream = AudioSystem.getAudioInputStream(new File("Music/shoot.wav"));
				} catch (UnsupportedAudioFileException | IOException e) {
					e.printStackTrace();
				}
				break;
			case "ice":
				color = new Color(0, 210, 255);
				try {
					stream = AudioSystem.getAudioInputStream(new File("Music/Ice Beam Shot.wav"));
				} catch (UnsupportedAudioFileException | IOException e) {
					e.printStackTrace();
				}
				break;
		}
		
		try {
			Clip shoop = AudioSystem.getClip();
			shoop.open(stream);
			shoop.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dx = 0;
		dy = 0;
		
		hitBox = new Area(new Rectangle((int) x, (int) y, (int) w, (int) h));
		weaponBox = new WeaponBox(hitBox, damage);
	}

	@Override
	public void draw(Graphics2D g) {
		
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.rotate(angle);
		
		g.transform(at);
		g.setColor(color);
		g.fillOval(0, 0, (int) w, (int) h);
		
		move();
		
	}
	
	private void updateHitBox() {
		hitBox = new Area(new Rectangle((int) x, (int) y, (int) w, (int) h));
		weaponBox.set(hitBox);
	}
	
	private void move() {
		if (angle == Math.PI / 2.0) {
			dy = -3 * velModifier;
			dx = 0;
		} else {
			dy = Math.tan(angle) * velModifier;
			dx = 2.0 * velModifier;
		}
		
		y += dy;
		x += dx;
		
		updateHitBox();
		
		if (instance.getRoomBounds().intersects(hitBox.getBounds2D()))
			isRemoved = true;
		for (AttackBox box : instance.getEnemyManager().getAttackBoxes()) {
			if (box.intersects(hitBox.getBounds2D()))
				isRemoved = true;
		}
	}
	
	public boolean isAlive() {
		return !isRemoved;
	}
	
	protected void setColor(Color c) {
		color = c;
	}

}
