package tiles;

import java.awt.Rectangle;
import java.awt.geom.Area;

import entity.Tile;

public class RectangularTile extends Tile {
	
	public RectangularTile() {
		hitbox = new Area(new Rectangle(0, 0, 20, 20));
	}
	
	public RectangularTile(int xIndex, int yIndex, double x, double y) {
		super(xIndex, yIndex, x, y);
		hitbox = new Area(new Rectangle((int) x, (int) y, 20, 20));
	}
	
}
