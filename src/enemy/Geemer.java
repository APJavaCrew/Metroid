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
	
	enum SpriteMotion {
		WALK, TURN
	}
	
	SpriteMotion spriteMotion = SpriteMotion.WALK;
	
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
		
		
		
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.scale(size, size);
		g2d.setTransform(at);
		g2d.drawImage(animation.getSprite(), 0, 0, null);
		animation.update();
		if (Constants.SHOWHITBOXES) {
			g.setColor(new Color(255, 255, 255));
			g.fillRect(hitBox.getBounds().x - (int) x, hitBox.getBounds().y - (int) y, hitBox.getBounds().width / size, hitBox.getBounds().height / size);
			g.setColor(new Color(0, 255, 255));
			g.fillRect(landBox.getBounds().x - (int) x, landBox.getBounds().y - (int) y, landBox.getBounds().width / size, landBox.getBounds().height / size);
			g.fillRect(topBox.getBounds().x - (int) x, topBox.getBounds().y - (int) y, topBox.getBounds().width / size, topBox.getBounds().height / size);
		}
		move();
	}
	
	public void move() {
		x += dx;
		y += dy;

		updateHitBoxes();
		checkCollision();
	}
	
	public void checkCollision() {
		ArrayList<Tile> tiles = instance.getRoom().getIntersectingTiles(this);
		if (tiles.size() > 0) {
			for (Tile e : tiles) {
				if (landBox.intersects(e.getHitBox().getBounds2D())) {
					dy = 0;
				}
			}
		}
		else
			fall();
	}
	
	protected void updateHitBoxes() {
		Rectangle hitBoxRect = new Rectangle((int) x, (int) y + 5, w, h - 10);
		hitBox = new Area(hitBoxRect);
		Rectangle landBoxRect = new Rectangle((int) (x + w / 2 - 2), (int) (h + y - 5), 4, 5);
		landBox = new Area(landBoxRect);
		
		attackBox = new AttackBox(hitBox, 10);
	}

	private void fall() {
		dy += Constants.GRAVITY_ACCEL;
	}
	
	public AttackBox getAttackBox() {
		return attackBox;
	}
	
}
