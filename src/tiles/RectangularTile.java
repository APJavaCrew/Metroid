package tiles;

import java.awt.Rectangle;
import java.awt.geom.Area;

public class RectangularTile extends Tile {
	
	public RectangularTile() {
		hitBox = new Area(new Rectangle(0, 0, 81, 85));
	}
	
	public RectangularTile(double x, double y) {
		super(x, y);
		hitBox = new Area(new Rectangle((int) x, (int) y, 81, 85));
	}
	
}
