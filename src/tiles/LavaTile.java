package tiles;

import java.awt.Graphics;

import entity.Tile;
import sprite.SpriteSheet;

public class LavaTile extends Tile {

	public LavaTile(int xIndex, int yIndex, double x, double y) {
		super(xIndex, yIndex, x, y);
		this.sprite = new SpriteSheet("red.png", 20, 20);
	}
}