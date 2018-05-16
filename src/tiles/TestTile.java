package tiles;

import java.awt.Graphics;

import sprite.SpriteSheet;

public class TestTile extends Tile {

	public TestTile(double x, double y) {
		super(x, y);
		this.sprite = new SpriteSheet("red.png", 20, 20);
	}

}
