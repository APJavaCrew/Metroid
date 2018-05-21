package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;

import entity.Being;
import main.Constants;
import main.Runner;
import sprite.Animation;
import sprite.SpriteSheet;
import tiles.Tile;
import weapon.Beam;

public class Player extends Being {
	
	SpriteSheet sandman = new SpriteSheet("SamWalkLeft.png", 32, 37);

	Animation walkLeft = new Animation(sandman.getSpritesAt("10-0-1-2-3-4-5-6-7-8-9-10", "0-0-0-0-0-0-0-0-0-0"), 60, true);
	
	int size = 3;

	private boolean stopL = false, stopR = false;
	Runner instance;
	
	ArrayList<Beam> beams = new ArrayList<Beam>();
	
	public Player() {
		
		walkLeft.start();
		
		x = 100;
		y = 450;
		dx = 0;
		dy = 0;
		
		Rectangle hitBoxRect = new Rectangle((int) x, (int) y, walkLeft.getSprite().getWidth() * size, walkLeft.getSprite().getHeight() * size); //use this to translate the hitBox
		hitBox = new Area(hitBoxRect);
		
		w = walkLeft.getSprite().getWidth() * size;
		h = walkLeft.getSprite().getHeight() * size;
	}
	
	public Player(double x, double y, double dx, double dy) {
		this.x = x; 
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		
		Rectangle hitBoxRect = new Rectangle((int) x, (int) y, walkLeft.getSprite().getWidth() * size, walkLeft.getSprite().getHeight() * size); //use this to translate the hitBox
		hitBox = new Area(hitBoxRect);
	}
	
	@Override
	public void draw(Graphics g) {
		int newW = walkLeft.getSprite().getWidth() * size, newH = walkLeft.getSprite().getHeight() * size;
		Image temp = walkLeft.getSprite().getScaledInstance(newW, newH, Image.SCALE_REPLICATE);
		
//		g.setColor(new Color(255, 255, 255));
//		g.fillRect(hitBox.getBounds().x, hitBox.getBounds().y, hitBox.getBounds().width, hitBox.getBounds().height);
//		g.setColor(new Color(0, 255, 255));
//		g.fillRect(landBox.getBounds().x, landBox.getBounds().y, landBox.getBounds().width, landBox.getBounds().height);
//		g.fillRect(topBox.getBounds().x, topBox.getBounds().y, topBox.getBounds().width, topBox.getBounds().height);
		
		g.drawImage(temp, (int) x, (int) y, null);
		walkLeft.update();
		move();
	}
	
	public void left() {
		if(Math.abs(dx) > Constants.MAX_RUN_SPEED) {
			dx -= Constants.RUN_ACCEL;
		} else {
			dx = -1;
		}
	}
	
	public void right() {
		if(Math.abs(dx) < Constants.MAX_RUN_SPEED) {
			dx += Constants.RUN_ACCEL;
		} else {
			dx = 1;
		}
	}
	
	public void decelerate() {
		if(dx > Constants.BRAKE_DEADZONE)
			dx -= Constants.BRAKE_ACCEL;
		else if(dx < (-1 * Constants.BRAKE_DEADZONE))
			dx += Constants.BRAKE_ACCEL;
		else
			dx = 0;
	}
	
	public void jump() {
		if (instance.getRoomBounds().intersects(landBox.getBounds2D()))
			dy = -6;
	}
	
	public void fall() {
		if(!instance.getRoomBounds().intersects(landBox.getBounds2D()) && !instance.getRoomBounds().intersects(topBox.getBounds2D())) {
			dy += Constants.GRAVITY_ACCEL;
		
		} else if (instance.getRoomBounds().intersects(topBox.getBounds2D())) {
			dy = 0.15;
		} else /*player is on the ground*/ {
			dy = 0;
		}
	}
	
	public void checkCollision() {
		ArrayList<Tile> tiles = instance.getRoom().getIntersectingTiles(this);
		if (tiles.size() > 0) {
			for (Tile e : tiles) {
				if (hitBox.intersects(e.getHitBox().getBounds2D())) {
					if (e.getX() < this.x)
						stopL = true;
					else if (e.getX() > this.x)
						stopR = true;
				} else {
					stopL = false;
					stopR = false;
				}
			}
		} else {
			stopL = false;
			stopR = false;
		}
		if (stopL && stopR)
			dy = -0.1;
		else if (stopL && dx < 0)
			dx = 0;
		else if (stopR && dx > 0)
			dx = 0;
	}
	
	private void updateHitBoxes() {
		Rectangle hitBoxRect = new Rectangle((int) x, (int) y + 5, w, h - 10);
		hitBox = new Area(hitBoxRect);
		Rectangle landBoxRect = new Rectangle((int) (x + 2), (int) (h + y - 5), w - 4, 5);
		landBox = new Area(landBoxRect);
		Rectangle topBoxRect = new Rectangle((int) (x + 2), (int) y, w - 4, 5);
		topBox = new Area(topBoxRect);
	}
	
	@Override
	public void move() {
		
		dx = Math.pow(instance.getAxis1()[3], 3);
		
		checkCollision();
		
		x += dx;
		y += dy;
		
		updateHitBoxes();
		
		
		if (instance.getButt1()[1]) {
			jump();
		}
		
		fall();
		
		updateBeams();
		
	}
	
	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}
	
	public void updateInstance(Runner in) {
		instance = in;
		for (Beam b : beams)
			b.updateInstance(in);
	}

	public void fire() {
		beams.add(new Beam(30, 1, 1, x - 10, y + 20));
		beams.get(beams.size() - 1).updateInstance(instance);
	}
	
	public void updateBeams() {
		for (int i = 0; i < beams.size(); i++) {
			beams.get(i).updateInstance(instance);
			if (!beams.get(i).isAlive())
				beams.remove(beams.get(i));
		}
	}
	
	public ArrayList<Beam> getBeams() {
		return beams;
	}
	
}