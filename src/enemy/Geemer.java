package enemy;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;

import entity.Enemy;
import main.Runner;
import sprite.Animation;
import sprite.SpriteSheet;
import tiles.Tile;
import weapon.Beam;

public class Geemer extends Enemy {
	double health=15;
	
	double attack=10;
	SpriteSheet geemupright = new SpriteSheet("Geemer.png", 32, 37);
	Animation geemuprightMove = new Animation(geemupright.getSpritesAt("10-0-1-2-3-4-5-6-7-8-9-10", "0-0-0-0-0-0-0-0-0-0"), 60, 1, true);
	
	int size=3;
	public Geemer() {
		geemuprightMove.start();
		x = 100;
		y = 450;
		dx = 0;
		dy = 0;
		
		Rectangle hitBoxRect = new Rectangle((int) x, (int) y, geemuprightMove.getSprite().getWidth() * size, geemuprightMove.getSprite().getHeight() * size); //use this to translate the hitBox
		hitBox = new Area(hitBoxRect);
		
		w = geemuprightMove.getSprite().getWidth() * size;
		h = geemuprightMove.getSprite().getHeight() * size;
	}
	public void draw(Graphics g) {
		int newW = geemuprightMove.getSprite().getWidth() * size, newH = geemuprightMove.getSprite().getHeight() * size;
		Image temp = geemuprightMove.getSprite().getScaledInstance(newW, newH, Image.SCALE_REPLICATE);
		
//		g.setColor(new Color(255, 255, 255));
//		g.fillRect(hitBox.getBounds().x, hitBox.getBounds().y, hitBox.getBounds().width, hitBox.getBounds().height);
//		g.setColor(new Color(0, 255, 255));
//		g.fillRect(landBox.getBounds().x, landBox.getBounds().y, landBox.getBounds().width, landBox.getBounds().height);
//		g.fillRect(topBox.getBounds().x, topBox.getBounds().y, topBox.getBounds().width, topBox.getBounds().height);
		
		g.drawImage(temp, (int) x, (int) y, null);
		geemuprightMove.update();
		move();
	}
	public void checkCollision() {
		ArrayList<Tile> tiles = instance.getRoom().getIntersectingTiles(this);
		if (tiles.size() > 0) {
			for (Tile e : tiles) {
				if (hitBox.intersects(e.getHitBox().getBounds2D())) {
					
	
	}
	
	

}
