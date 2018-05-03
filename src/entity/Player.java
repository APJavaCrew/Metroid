package entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import sprite.Animation;
import sprite.SpriteSheet;

public class Player extends Being {

	SpriteSheet sandman = new SpriteSheet("SamWalkLeft.png", 32, 37);
	Animation walkLeft = new Animation(sandman.getSpritesAt("10-0-1-2-3-4-5-6-7-8-9-10", "0-0-0-0-0-0-0-0-0-0"), 170, true);
	
	public Player() {
		walkLeft.start();
	}
	
	@Override
	public void draw(Graphics g) {
		int newW = walkLeft.getSprite().getWidth() * 4, newH = walkLeft.getSprite().getHeight() * 4;
		Image temp = walkLeft.getSprite().getScaledInstance(newW, newH, Image.SCALE_REPLICATE);
		g.drawImage(sandman.getSpriteAt(0, 0), 100, 100, null);
		g.drawImage(temp, 200, 100, null);
		walkLeft.update();
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	
}
