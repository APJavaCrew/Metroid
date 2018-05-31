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
	
	@Override
	public void draw(Graphics2D g) {
		
		at = new AffineTransform();
		at.translate(x, y);
		at.scale(size, size);
		
		g.transform(at);
		g.drawImage(animation.getSprite(), 0, 0, null);
		
		System.out.println("GREG");
		
		if (Constants.SHOWHITBOXES) {
			at = new AffineTransform();
			at.translate(x, y);
			g.transform(at);
			g.setColor(new Color(255, 255, 255, 175));
			g.fill(hitBox);
			g.setColor(new Color(255, 0, 0, 90));
			g.fill(viewBox);
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
