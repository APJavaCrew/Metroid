package tiles;

import java.awt.Polygon;
import java.awt.geom.Area;

public class TriangularTileLT extends Tile {

	public TriangularTileLT() {
		int[] xs = {0, 0, 20};
		int[] ys = {0, 20, 0};
		hitBox = new Area(new Polygon(xs, ys, 3));
	}
	
}
