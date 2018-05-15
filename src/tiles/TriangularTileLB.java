package tiles;

import java.awt.Polygon;
import java.awt.geom.Area;

import entity.Tile;

public class TriangularTileLB extends Tile {
	
	public TriangularTileLB() {
		int[] xs = {0, 0, 20};
		int[] ys = {0, 20, 20};
		hitBox = new Area(new Polygon(xs, ys, 3));
	}

}
