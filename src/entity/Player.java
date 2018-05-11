package entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import main.Constants;
import main.Runner;
import sprite.Animation;
import sprite.SpriteSheet;

public class Player extends Being {
	
	SpriteSheet sandman = new SpriteSheet("SamWalkLeft.png", 32, 37);

	Animation walkLeft = new Animation(sandman.getSpritesAt("10-0-1-2-3-4-5-6-7-8-9-10", "0-0-0-0-0-0-0-0-0-0"), 40, true);
	

	Rectangle hitboxRect = new Rectangle(0, 0, walkLeft.getSprite().getWidth(), walkLeft.getSprite().getHeight()); //use this to translate the hitbox
	Area hitbox = new Area(hitboxRect);
	private double x, y; //position
	private double dx, dy; //horiz/vert speed
	Runner instance;
	
	public Player() {
		
		walkLeft.start();
		
		x = 500;
		y = 500;
		dx = 0;
		dy = 0;
	}
	
	public Player(double x, double y, double dx, double dy) {
		this.x = x; 
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
	
	@Override
	public void draw(Graphics g) {
		int newW = walkLeft.getSprite().getWidth() * 3, newH = walkLeft.getSprite().getHeight() * 3;
		Image temp = walkLeft.getSprite().getScaledInstance(newW, newH, Image.SCALE_REPLICATE);
		g.drawImage(temp, (int) x, (int) y, null);
		walkLeft.update();
		move();
	}
	
	public void left() {
		if(Math.abs(dx) > Constants.MAX_RUN_SPEED) {
			dx -= Constants.RUN_ACCEL;
		} else {
			dx = -1;
		}
	}
	
	public void right() {
		if(Math.abs(dx) < Constants.MAX_RUN_SPEED) {
			dx += Constants.RUN_ACCEL;
		} else {
			dx = 1;
		}
	}
	
	public void decelerate() {
		if(dx > Constants.BRAKE_DEADZONE)
			dx -= Constants.BRAKE_ACCEL;
		else if(dx < (-1 * Constants.BRAKE_DEADZONE))
			dx += Constants.BRAKE_ACCEL;
		else
			dx = 0;
	}
	
	public void jump() {
		dy = -1;
	}
	
	public void fall() {
		if(dy < Constants.TERMINAL_VELOCITY) {/*&& player is not on the ground*/
			dy += Constants.GRAVITY_ACCEL;
		} else if(dy >= Constants.TERMINAL_VELOCITY /*&& player is not on the ground*/) {
			dy = Constants.TERMINAL_VELOCITY;
		} else /*player is on the ground*/ {
			dy = 0;
		}
	}
	
	public void checkCollision(Runner runner) {
		
	}
	
	@Override
	public void move() {
		x += dx;
		y += dy;
		hitboxRect.x += dx;
		hitboxRect.y += dy;
		hitbox = new Area(hitboxRect);
		
		dx = Math.pow(instance.getAxis1()[3], 3);
		/*if(instance.getAxis1()[3] < 0)
			dx *= -1;*/
		
		if (instance.getButt1()[1]) {
			jump();
		}
		
		//fall();
		
	}
	
	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}
	
	public void getInstance(Runner in) {
		instance = in;
	}
	
}