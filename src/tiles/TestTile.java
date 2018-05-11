package tiles;

import java.awt.Graphics;

import entity.Tile;
import sprite.SpriteSheet;

public class TestTile extends Tile {

	public TestTile(int xIndex, int yIndex, double x, double y) {
		super(xIndex, yIndex, x, y);
		this.sprite = new SpriteSheet("red.png", 20, 20);
	}

}
