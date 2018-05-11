package entity;

import java.awt.Graphics;
import java.awt.Image;

public abstract class Being extends Entity {

	protected boolean isAlive = true;
	protected double health;
	
	
	public Being(double x, double y) {
		super(x, y);
	}
	
	public Being(double x, double y, int w, int h) {
		super(x, y, w, h);
	}
	
	public Being() {
		super();
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	public abstract void move();
	

}
