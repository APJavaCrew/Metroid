package entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;

import tiles.Tile;

public abstract class Being extends Entity {

	protected boolean isAlive = true;
	protected double health;
	
	protected Area landBox, topBox;
	
	public Being(double x, double y) {
		super(x, y);
		landBox = new Area(new Rectangle((int) (w / 2.0  + x - 5), (int) (h + y - 5), 5, 5));
		topBox = new Area(new Rectangle((int) (x + 2), (int) y, w - 4, 5));
	}
	
	public Being(double x, double y, int w, int h) {
		super(x, y, w, h);
		landBox = new Area(new Rectangle((int) (w / 2.0  + x - 5), (int) (h + y - 5), 5, 5));
		topBox = new Area(new Rectangle((int) (x + 2), (int) y, w - 4, 5));
	}
	
	public Being() {
		super();
		landBox = new Area(new Rectangle((int) (w / 2.0  + x - 5), (int) (h + y - 5), 5, 5));
		topBox = new Area(new Rectangle((int) (x + 2), (int) y, w - 4, 5));
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	public abstract void move();
	public abstract void checkCollision(); 
		
		

}
