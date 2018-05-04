package tiles;

import java.awt.Graphics;

import entity.Tile;
import sprite.SpriteSheet;

public class LavaTile extends Tile
{
	SpriteSheet lava;

public LavaTile()
{
	lava = new SpriteSheet("SamWalkLeft.png", 20, 20);
}
public void draw(Graphics g) {
	g.drawImage(lava.getSpriteAt(0, 0), 100, 100, null);
}
}