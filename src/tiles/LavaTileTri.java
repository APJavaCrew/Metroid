package tiles;

import sprite.SpriteSheet;

public class LavaTileTri extends Tile {

	public LavaTileTri(double x, double y) {
		super(x, y);
		sprite = new SpriteSheet("LavaTileTri.png", 20, 20);
	}
	
}
