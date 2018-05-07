package tiles;

import entity.Tile;
import sprite.SpriteSheet;

public class LavaTileTri extends Tile {

	public LavaTileTri(int xIndex, int yIndex, double x, double y) {
		super(xIndex, yIndex, x, y);
		sprite = new SpriteSheet("LavaTileTri.png", 20, 20);
	}
	
}
