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
	
	Animation animation = Constants.geemerWalk;
	Animation legs = Constants.geemerLegz;
	
	AffineTransform at;
	
	Rectangle2D landBoxRect;
	Area rightBox;
	
	int turnWait = 0;
	
	double speed = 3.0;
	int size = 3;
	
	private boolean turning, turningOtherWay, falling;
	
	double angle = 0;
	
	enum SpriteMotion {
		WALK, DIE
	}
	
	enum Direction {
		UP, RIGHT, DOWN, LEFT
	}
	
	Direction direction = Direction.UP;
	
	SpriteMotion spriteMotion = SpriteMotion.WALK;
	
	public Geemer(double x, double y, int direc, Runner in) {
		
		instance = in;
		
		animation.restart();
		legs.restart();
		
		switch (direc) {
			default: break;
			case 1:
				direction = Direction.UP;
				break;
			case 2:
				direction = Direction.RIGHT;
				angle = Math.PI / 2.0;
				break;
			case 3:
				direction = Direction.DOWN;
				angle = Math.PI;
				break;
			case 4:
				direction = Direction.LEFT;
				angle = 3.0 * Math.PI / 2.0;
				break;
		}
		
		spriteMotion = SpriteMotion.WALK;
		
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		
		size = 3;
		
		health = 15;
		
		turnWait = 0;
		
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
		
		w = legs.getSprite().getWidth() * size;
		h = legs.getSprite().getHeight() * size;
	}
	
	public void draw(Graphics2D g) {
		
		if (!isHurt) {
			at = new AffineTransform();
			at.translate(x, y);
			at.rotate(angle, w / 2, h / 2);
	
			g.transform(at);
			g.drawImage(legs.getSprite(), 0, 0, w, h, null);
			g.drawImage(animation.getSprite(), 0, 0, w, h, null);
			animation.update();
			legs.update();
		} else {
			if (hurtTimeout < 1)
				hurtTimeout++;
			else
				isHurt = false;
		}
		
		
		if (Constants.SHOWHITBOXES) {
			at = new AffineTransform();
			at.translate(x, y);
			g.transform(at);
			g.setColor(new Color(255, 255, 255, 175));
		    g.fill(hitBox);
		    g.setColor(new Color(0, 255, 255, 175));
		    g.fill(landBox);
		    g.fill(rightBox);
		}
		
		move();
		
	}
	
	public void move() {
		if (!falling && !turning && !turningOtherWay) {
			switch (direction) {
				default:
					dy = 0;
					dx = speed;
					break;
				case UP:
					dy = 0;
					dx = speed;
					break;
				case RIGHT:
					dx = 0;
					dy = speed;
					break;
				case DOWN:
					dx = -speed;
					dy = 0;
					break;
				case LEFT:
					dx = 0;
					dy = -speed;
					break;
			}
		} else if (turning) {
			dy = 0;
			dx = 0;
			turn();
		} else if (turningOtherWay) {
			dy = 0;
			dx = 0;
			turnOtherWay();
		} else
			fall();
		
		x += dx;
		y += dy;

		updateHitBoxes();
		checkCollision();
		checkBeingHurt();
	}
	
	public void checkCollision() {
		ArrayList<Tile> tiles = instance.getRoom().getIntersectingTiles(hitBox);
		ArrayList<Tile> landTiles = instance.getRoom().getIntersectingTiles(landBox);
		ArrayList<Tile> rightTiles = instance.getRoom().getIntersectingTiles(rightBox);
		if (tiles.size() > 0) {
			falling = false;
			if (landTiles.size() == 0) {
				turning = true;
			} else if (rightTiles.size() > 0) {
				turnWait = 0;
				turningOtherWay = true;
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
				direction = Direction.RIGHT;
				break;
			case RIGHT:
				direction = Direction.DOWN;
				break;
			case DOWN:
				direction = Direction.LEFT;
				break;
			case LEFT:
				direction = Direction.UP;
				break;
		}

		angle += Math.PI / 2.0;

		turning = false;
		
	}
	
	private void turnOtherWay() {
		
		if (turnWait == 0) {
			switch (direction) {
				default:
					break;
				case UP:
					direction = Direction.LEFT;
					break;
				case RIGHT:
					direction = Direction.UP;
					break;
				case DOWN:
					direction = Direction.RIGHT;
					break;
				case LEFT:
					direction = Direction.DOWN;
					break;
			}
		}
		
		angle -= Math.PI / 2.0;
		turningOtherWay = false;
		
		turnWait ++;
		
		if (turnWait == 10)
			turnWait = 0;
		
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
		
		Rectangle2D rightBoxRect = new Rectangle(w - 19, h / 2 - 5, 5, 10);
		rightBox = new Area(rightBoxRect);
		rightBox.transform(at);
		
		attackBox = new AttackBox(hitBox, 10);
	}

	private void fall() {
		dy += Constants.GRAVITY_ACCEL;
	}
	
	public AttackBox getAttackBox() {
		return attackBox;
	}
	
}
