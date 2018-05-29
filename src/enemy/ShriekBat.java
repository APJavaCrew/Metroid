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
	Graphics2D g2d;
	
	Area viewBox;
	int viewBoxW, viewBoxH;
	int size;
	
	public ShriekBat(double x, double y) {
		
		animation = Constants.shriekBat;
		animation.start();
		
		this.x = x; this.y = y;
		size = 3;
		dx = 0;
		dy = 0;
		
		w = animation.getSprite().getWidth();
		h = animation.getSprite().getHeight();
		
		at = new AffineTransform();
		at.translate(x, y);
		
		hitBox = new Area(new Rectangle(0, 5, w, h - 5) );
		hitBox.transform(at);
		
		viewBoxW = 300; viewBoxH = 500;
		viewBox = new Area( new Rectangle(0, 0, viewBoxW, viewBoxH) );
		viewBox.transform(at);
		
	}
	
	public void draw(Graphics g) {
		
		g2d = (Graphics2D) g;
		x = 500; y = 500;
		at = new AffineTransform();
		at.translate(x, y);
		at.scale(size, size);
		
		g2d.setTransform(at);
		g2d.drawImage(animation.getSprite(), 0, 0, null);
		
		if (Constants.SHOWHITBOXES) {
			at = new AffineTransform();
			at.translate(x, y);
			g2d.setColor(new Color(255, 255, 255, 175));
			g2d.fill(hitBox);
			g2d.setColor(new Color(255, 0, 0, 90));
			g2d.fill(viewBox);
		}
		
		move();
		
	}
	
	public void move() {
		y += dy;
		
		animation.update();
	}
	
	public void updateHitBoxes() {
		at = new AffineTransform();
		Rectangle hitBoxRect = new Rectangle(0, 5, w, h - 10);
		hitBox = new Area(hitBoxRect);
		hitBox.transform(at);
		Rectangle landBoxRect = new Rectangle(2, h - 5, w - 4, 5);
		landBox = new Area(landBoxRect);
		landBox.transform(at);
		
		attackBox.set(hitBox);
		
	}

}
