package entity;

import java.awt.Graphics;
import java.awt.Image;

import main.Constants;
import sprite.Animation;
import sprite.SpriteSheet;

public class Player extends Being {
	
	SpriteSheet sandman = new SpriteSheet("SamWalkLeft.png", 32, 37);
	Animation walkLeft = new Animation(sandman.getSpritesAt("10-0-1-2-3-4-5-6-7-8-9-10", "0-0-0-0-0-0-0-0-0-0"), 1, true);
	
	private int x, y;
	private double dx, dy;
	
	public Player() {
		
		walkLeft.start();
		
		x = 0;
		y = 0;
		dx = 0;
		dy = 0;
	}
	
	public Player(int x, int y, double dx, double dy) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(walkLeft.getSprite(), 200, 100, null);
		g.drawImage(sandman.getSpritesAt("1-0", "0")[0], 200, 100, null);
		g.drawImage(sandman.getSpriteAt(0, 0), 100, 100, null);
		g.drawImage(walkLeft.get(), 200, 100, null);
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
		if(dy < Constants.TERMINAL_VELOCITY /*&& player is not on the ground*/) {
			dy += Constants.GRAVITY_ACCEL;
		} else if(dy >= Constants.TERMINAL_VELOCITY /*&& player is not on the ground*/) {
			dy = Constants.TERMINAL_VELOCITY;
		} else /*player is on the ground*/ {
			dy = 0;
		}
	}
	
	@Override
	public void move() {
		
		x += dx;
		y += dy;
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
	
}