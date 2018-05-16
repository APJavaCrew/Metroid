package tiles;

import java.awt.Graphics;

import sprite.SpriteSheet;

public class MetalTile extends RectangularTile {

	public MetalTile(double x, double y) {
		super(x, y);
		this.sprite = new SpriteSheet("metalTile.png", 20, 20);
	}
}