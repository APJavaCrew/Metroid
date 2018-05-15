package entity;

import java.awt.Graphics;
import java.awt.geom.Area;

public abstract class Entity {
	
	protected double x, y, dx, dy;
	protected int w, h;
	protected boolean removed = false;
	
	protected Area hitBox;
	
	public Entity(double x, double y) {
		this.x = x;
		this.y = y;
		this.w = 1;
		this.h = 1;
	}
	
	public Entity(double x, double y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public Entity() {
		this.x = 0;
		this.y = 0;
		this.w = 1;
		this.h = 1;
	}
	
	public void setW(int w) { this.w = w; }
	public void setH(int h) { this.h = h; }
	
	public abstract void draw(Graphics g);
	
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	public double getW() { return this.w; }
	public double getH() { return this.h; }
	
	public Area getHitBox() { return hitBox; }
	
}
