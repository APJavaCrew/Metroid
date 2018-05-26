package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;

import main.Constants;
import main.Runner;
import sprite.Animation;
import tiles.Tile;

public class Ripper extends Enemy {
	
	Graphics2D g2d;
	
	Animation animation;
	
	AffineTransform at;

	Area frontBox;
	
	private boolean isLeft, turning;
	int dir, size, turnWait;
	
	
	public Ripper(double x, double y, Runner in) {
		
		instance = in;
		
		animation = Constants.ripperL;
		
		this.x = x; this.y = y;
		
		w = animation.getSprite().getWidth();
		h = animation.getSprite().getHeight();
		
		animation.start();
		
		dir = -1;
		size = 3;
		turnWait = 0;
		
		isLeft = true;
		turning = false;
		
		at = new AffineTransform();
		at.translate(x, y);
		at.scale(1, 1);
		hitBox = new Area(new Rectangle(0, 0, w, h));
		frontBox = new Area(new Rectangle(-3, h / 2 - 5, 5, 10));
	}
	
	public void draw(Graphics g) {
		g2d = (Graphics2D) g;
		
		at = new AffineTransform();
		at.translate(x, y);
		at.scale(size, size);
		g2d.setTransform(at);
		g2d.drawImage(animation.getSprite(), 0, 0, null);
		
		if (Constants.SHOWHITBOXES) {
			at = new AffineTransform();
			
			g2d.setTransform(at);
			g2d.setColor(new Color(255, 255, 255, 175));
			g2d.fill(hitBox);
			g2d.setColor(new Color(0, 255, 255, 175));
			g2d.fill(frontBox);
		}
		
		animation.update();
		move();
		updateHitBoxes();
	}
	
	public void move() {
		
		if (!turning)
			dx = dir;
		else
			dx = 0;
		
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
	}
	
}
