package tiles;

import java.awt.Rectangle;
import java.awt.geom.Area;

import main.Constants;

public class RectangularTile extends Tile {
	
	public RectangularTile() {
		hitBox = new Area(new Rectangle(0, 0, Constants.TILESIZE, Constants.TILESIZE));
	}
	
	public RectangularTile(double x, double y) {
		super(x, y);
		hitBox = new Area(new Rectangle((int) x, (int) y, Constants.TILESIZE, Constants.TILESIZE));
	}
	
}
