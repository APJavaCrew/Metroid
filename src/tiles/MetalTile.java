package tiles;

import java.awt.Graphics;

import entity.Tile;
import sprite.SpriteSheet;

public class MetalTile extends RectangularTile {

	public MetalTile(int xIndex, int yIndex, double x, double y) {
		super(xIndex, yIndex, x, y);
		this.sprite = new SpriteSheet("metalTile.png", 20, 20);
	}
}