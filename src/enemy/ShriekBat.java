package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import sprite.Animation;
import main.Constants;

public class ShriekBat extends Enemy {
	
	Animation animation;
	
	AffineTransform at;
	
	Area viewBox;
	int viewBoxW, viewBoxH;
	int size, dieTimeout;
	
	public ShriekBat(double x, double y) {
		
		super(x, y);
		animation = Constants.shriekBat;
		animation.start();
		
		size = 3;
		
		dieTimeout = 0;
		health = 25;
		
		w = animation.getSprite().getWidth() * size;
		h = animation.getSprite().getHeight() * size;
		
		at = new AffineTransform();
		at.translate(x, y);
		
		hitBox = new Area(new Rectangle(0, 0, w, h) );
		hitBox.transform(at);

		landBox = new Area(new Rectangle(w / 2 - 1, h, 2, 3));
		landBox.transform(at);
		
		viewBoxW = 300; viewBoxH = 500;
		viewBox = new Area( new Rectangle(-viewBoxW / 2, 0, viewBoxW, viewBoxH) );
		viewBox.transform(at);
		
		attackBox = new AttackBox(hitBox, 10);
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (!isHurt) {
			at = new AffineTransform();
			at.translate(x, y);
	
			g.transform(at);
			g.drawImage(animation.getSprite(), 0, 0, w, h, null);
			animation.update();
		} else {
			if (hurtTimeout < 1)
				hurtTimeout++;
			else
				isHurt = false;
		}
		
		if (Constants.SHOWHITBOXES) {
			at = new AffineTransform();
			at.translate(0, 0);
			g.setTransform(at);
			g.setColor(new Color(255, 255, 255, 175));
			g.fill(hitBox);
			g.setColor(new Color(255, 0, 0, 50));
			g.fill(viewBox);
		}
		
		move();
		
	}
	
	public void move() {
		updateHitBoxes();
		
		y += dy;
		x += dx;
		
		if (instance.getRoomBounds().intersects(landBox.getBounds2D()))
			floorDie();
		else if (instance.getPlayer().getHitBox().intersects(viewBox.getBounds2D()))
			attack();
		
		checkBeingHurt();
		animation.update();
	}
	
	public void attack() {
		double playerX = instance.getPlayer().getX();
		
		dx = (playerX - x) * 0.025;
		dy = 6;

	}
	
	public void floorDie() {
		dx = 0;
		dy = 0;
		
		if (dieTimeout < 60)
			dieTimeout++;
		else {
			isAlive = false;
		}
		
	}
	
	@Override
	public void updateHitBoxes() {
		at = new AffineTransform();
		at.translate(x, y);
		Rectangle hitBoxRect = new Rectangle(0, 0, w, h);
		hitBox = new Area(hitBoxRect);
		hitBox.transform(at);
		landBox = new Area(new Rectangle(w / 2 - 1, h, 2, 3));
		landBox.transform(at);
		
		attackBox.set(hitBox);
		
	}

}
