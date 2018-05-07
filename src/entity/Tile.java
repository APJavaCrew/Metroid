package entity;

import java.awt.Graphics;
import java.awt.Image;

import sprite.SpriteSheet;

public class Tile extends Entity {

	int xIndex, yIndex;
	protected SpriteSheet sprite;
	
	public Tile() {
		super();
	}
	
	public Tile(int xIndex, int yIndex, double x, double y) {
		super(x, y, 20, 20);
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(sprite.getSheet(), (int) x, (int) y, null);
	}
	
	public int getXIndex() {
		return xIndex;
	}
	
	public int getYIndex() {
		return yIndex;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}

}
