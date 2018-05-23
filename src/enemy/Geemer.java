package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;

import main.Constants;
import main.Runner;
import sprite.Animation;
import sprite.SpriteSheet;
import tiles.Tile;
import weapon.Beam;

public class Geemer extends Enemy {
	
	Animation animation = Constants.geemerUR;
	Graphics2D g2d;
	
	int size;
	
	public Geemer(double x, double y) {
		animation.start();
		
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		
		size = 3;
		
		health = 15;
		
		Rectangle hitBoxRect = new Rectangle((int) x, (int) y, animation.getSprite().getWidth() * size, animation.getSprite().getHeight() * size); //use this to translate the hitBox
		hitBox = new Area(hitBoxRect);
		
		attackBox = new AttackBox(hitBox, 10);
		
		w = animation.getSprite().getWidth() * size;
		h = animation.getSprite().getHeight() * size;
	}
	
	public void draw(Graphics g) {
		
		g2d = (Graphics2D) g;
		
		System.out.println(x);
		if (Constants.SHOWHITBOXES) {
			g2d.setColor(new Color(255, 255, 255));
			g2d.fillRect(hitBox.getBounds().x, hitBox.getBounds().y, hitBox.getBounds().width, hitBox.getBounds().height);
			g2d.setColor(new Color(0, 255, 255));
			g2d.fillRect(landBox.getBounds().x, landBox.getBounds().y, landBox.getBounds().width, landBox.getBounds().height);
			g2d.fillRect(topBox.getBounds().x, topBox.getBounds().y, topBox.getBounds().width, topBox.getBounds().height);
		}
		
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.scale(size, size);
		g2d.setTransform(at);
		g2d.drawImage(animation.getSprite(), 0, 0, null);
		animation.update();
		
		move();
	}
	
	public void move() {
		x += dx;
		y += dy;
		
		checkCollision();
	}
	
	public void checkCollision() {
		ArrayList<Tile> tiles = instance.getRoom().getIntersectingTiles(this);
		if (tiles.size() > 0) {
			for (Tile e : tiles) {
				if (hitBox.intersects(e.getHitBox().getBounds2D())) {
					
				}
			}
		}
		else
			fall();
	}

	private void fall() {
		dy += Constants.GRAVITY_ACCEL;
	}
	
	public AttackBox getAttackBox() {
		return attackBox;
	}
	
}
