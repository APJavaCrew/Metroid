package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import main.Constants;
import main.Runner;
import sprite.Animation;
import sprite.SpriteSheet;
import tiles.Tile;
import weapon.Beam;

public class Geemer extends Enemy {
	
	Animation animation = Constants.geemerUR;
	Graphics2D g2d;
	AffineTransform at;
	
	Rectangle2D landBoxRect;
	Area rightBox;
	
	int size;
	
	private boolean turning, falling;
	
	double angle = 0;
	
	enum SpriteMotion {
		WALK, TURN
	}
	
	enum Direction {
		UP, RIGHT, DOWN, LEFT
	}
	
	Direction direction = Direction.UP;
	
	SpriteMotion spriteMotion = SpriteMotion.WALK;
	
	public Geemer(double x, double y) {
		
		animation.restart();
		
		direction = Direction.UP;
		spriteMotion = SpriteMotion.WALK;
		
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		
		size = 1;
		
		health = 15;
		
		falling = true;
		
		at = new AffineTransform();
		at.translate(x, y);
		at.scale(size, size);
		at.rotate(angle, w / 2 / size, h / 2 / size);
		
		Rectangle hitBoxRect = new Rectangle(0, 5, animation.getSprite().getWidth(), animation.getSprite().getHeight()); //use this to translate the hitBox
		hitBox = new Area(hitBoxRect);
		hitBox.transform(at);
		
		landBoxRect = new Rectangle(0, 0, 0, 0);
		rightBox = new Area(new Rectangle(0, 0, 0, 0));
		
		attackBox = new AttackBox(hitBox, 10);
		
		w = animation.getSprite().getWidth() * size;
		h = animation.getSprite().getHeight() * size;
	}
	
	public void draw(Graphics g) {
		
		g2d = (Graphics2D) g;

		at = new AffineTransform();
		at.translate(x, y);
		at.scale(size, size);
		at.rotate(angle, w / 2 / size, h / 2 / size);
		
		g2d.setTransform(at);
		g2d.drawImage(animation.getSprite(), 0, 0, null);
		animation.update();
		
		
		if (Constants.SHOWHITBOXES) {
			at = new AffineTransform();
			at.translate(x, y);
			at.scale(1, 1);
			at.rotate(angle, w / 2, h / 2);
			g2d.setTransform(at);
			g2d.setColor(new Color(255, 255, 255, 175));
		    g2d.fillRect(0, 0, w, h);
		    g2d.setColor(new Color(0, 255, 255, 175));
		    g2d.fillRect((int) landBoxRect.getX(), (int) landBoxRect.getY(), (int) landBoxRect.getWidth(),
		    		(int) landBoxRect.getHeight());
		    g2d.fillRect(w - 2, h / 2 - 5, 5, 10);
		}
		move();
	}
	
	public void move() {
		if (!falling && !turning) {
			switch (direction) {
				default:
					dy = 0;
					dx = 1;
					break;
				case UP:
					dy = 0;
					dx = 1;
					break;
				case RIGHT:
					dx = 0;
					dy = 1;
					break;
				case DOWN:
					dx = -1;
					dy = 0;
					break;
				case LEFT:
					dx = 0;
					dy = -1;
					break;
			}
		} else if (turning) {
			dy = 0;
			dx = 0;
			turn();
		} else
			fall();
		
		x += dx;
		y += dy;

		updateHitBoxes();
		checkCollision();
	}
	
	public void checkCollision() {
		ArrayList<Tile> tiles = instance.getRoom().getIntersectingTiles(hitBox);
		ArrayList<Tile> landTiles = instance.getRoom().getIntersectingTiles(landBox);
		if (tiles.size() > 0) {
			falling = false;
			if (landTiles.size() == 0) {
				turning = true;
			}
		} else {
			falling = true;
		}
	}
	
	private void turn() {
		
		switch (direction) {
			default:
				break;
			case UP:
				angle += Math.PI / 2.0;
				direction = Direction.RIGHT;
				turning = false;
				break;
			case RIGHT:
				angle += Math.PI / 2.0;
				direction = Direction.DOWN;
				turning = false;
				break;
			case DOWN:
				angle += Math.PI / 2.0;
				direction = Direction.LEFT;
				turning = false;
				break;
			case LEFT:
				angle += Math.PI / 2.0;
				direction = Direction.UP;
				turning = false;
				break;
		}
		
	}
	
	protected void updateHitBoxes() {
		at = new AffineTransform();
		at.translate(x, y);
		at.rotate(angle, w / 2, h / 2);
		Rectangle2D hitBoxRect = new Rectangle(0, 0, w, h);
		hitBox = new Area(hitBoxRect);
		hitBox.transform(at);
		landBoxRect = new Rectangle(12, h - 5, w - 24, 10);
		landBox = new Area(landBoxRect);
		landBox.transform(at);
		
		Rectangle2D rightBoxRect = new Rectangle(w - 2, h / 2 - 5, 5, 10);
		rightBox = new Area(rightBoxRect);
		
		attackBox = new AttackBox(hitBox, 10);
	}

	private void fall() {
		dy += Constants.GRAVITY_ACCEL;
	}
	
	public AttackBox getAttackBox() {
		return attackBox;
	}
	
}
