package entity;

import java.awt.Graphics;
import java.awt.Image;

public class Tile extends Entity {

	
	public Tile() {
		super();
	}
	
	public Tile(double x, double y) {
		super(x, y, 20, 20);
	}
	
	public Tile(int x, int y, double xP, double yP) {
		super(x, y, 20, 20);
		xIndex = x;
		yIndex = y;
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
