package entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import sprite.Animation;
import sprite.SpriteSheet;

public class TestEnemy extends Enemy {
	
	SpriteSheet sandman = new SpriteSheet("ZeroGravSuitStand.png", 24, 40);
	Rectangle hitbox = new Rectangle(0, 0, sandman.getSpriteAt(0, 0).getWidth(), sandman.getSpriteAt(0, 0).getHeight());
	private int x, y;
	private double dx, dy;
	int timer = 0;
	boolean timerIncreasing = true;

	@Override
	public void move() {
		if(timerIncreasing) {
			timer++;
			dx = 1;
		} else {
			timer--;
			dx = -1;
		}
		if(timer == 10)
			timerIncreasing = false;
		else if(timer == 0)
			timerIncreasing = true;
	}

	@Override
	public void draw(Graphics g) {
		move();
		g.drawImage(sandman.getSpriteAt(0, 0), (int) x, (int) y, null);
	}

	@Override
	public void setW(int w) {
		// TODO Auto-generated method stub
		super.setW(w);
	}

	@Override
	public void setH(int h) {
		// TODO Auto-generated method stub
		super.setH(h);
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return super.getX();
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return super.getY();
	}

	@Override
	public double getW() {
		// TODO Auto-generated method stub
		return super.getW();
	}

	@Override
	public double getH() {
		// TODO Auto-generated method stub
		return super.getH();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
