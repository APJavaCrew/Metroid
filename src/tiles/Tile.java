package tiles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Area;

import entity.Entity;
import main.Constants;
import sprite.SpriteSheet;

public class Tile extends Entity {

	protected SpriteSheet sprite;
	
	public Tile() {
		super();
	}
	
	public Tile(double x, double y) {
		super(x, y, 45, 45);
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(sprite.getSheet(), (int) x, (int) y, null);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}

}
