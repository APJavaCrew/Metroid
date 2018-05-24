package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Being;
import main.Constants;
import main.Runner;
import sprite.Animation;
import sprite.SpriteSheet;
import tiles.Tile;
import weapon.Beam;

public class Player extends Being {
	
	Animation animation = Constants.samStart;
	
	int size = 3;

	private boolean stopL = false, stopR = false;
	private boolean isOnGround = false;
	
	Runner instance;
	
	ArrayList<Beam> beams = new ArrayList<Beam>();
	private boolean charging = false;
	private double beamSize = 20.0;
	
	private Graphics2D g2d;
	
	private enum SpriteMotion {
		WALKLEFT, WALKRIGHT, JUMPSTILLLEFT, JUMPSTILLRIGHT,
		JUMPSPINLEFT, JUMPSPINRIGHT, STANDRIGHT, STANDLEFT,
		START
		
	};
	
	SpriteMotion spriteMotion;
	
	public Player() {
		
		spriteMotion = spriteMotion.START;
		
		animation.start();
		
		x = 100;
		y = 450;
		dx = 0;
		dy = 0;
		
		Rectangle hitBoxRect = new Rectangle((int) x, (int) y, w * size, h * size); //use this to translate the hitBox
		hitBox = new Area(hitBoxRect);
		
		w = animation.getSprite().getWidth() * size;
		h = animation.getSprite().getHeight() * size;
	}
	
	public Player(double x, double y, double dx, double dy) {

		spriteMotion = spriteMotion.START;

		animation.start();
		
		this.x = x; 
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		
		w = animation.getSprite().getWidth() * size;
		h = animation.getSprite().getHeight() * size;
		
		Rectangle hitBoxRect = new Rectangle((int) x, (int) y, w * size, h * size); //use this to translate the hitBox
		hitBox = new Area(hitBoxRect);
	}
	
	@Override
	public void draw(Graphics g) {
	    g2d = (Graphics2D) g;
		
		AffineTransform at = new AffineTransform();
	    at.translate(x, y);
	    at.scale(size, size);
	    g2d.setTransform(at);
	    updateSprite();
	    g2d.drawImage(animation.getSprite(), 0, 0, null);
	    
		if (Constants.SHOWHITBOXES) {
		    AffineTransform bt = new AffineTransform();
		    bt.translate(0, 0);
		    bt.scale(1, 1);
		    g2d.setTransform(bt);
		    g2d.setColor(new Color(255, 255, 255, 175));
		    g2d.fillRect( (int) hitBox.getBounds2D().getX(), (int) hitBox.getBounds2D().getY(),
		    		(int) hitBox.getBounds2D().getWidth(), (int) hitBox.getBounds2D().getHeight());
		    g2d.setColor(new Color(0, 255, 255, 175));
		    g2d.fillRect((int) landBox.getBounds2D().getX(), (int) landBox.getBounds2D().getY(), 
		    		(int) landBox.getBounds2D().getWidth(), (int) landBox.getBounds2D().getHeight());
		    g2d.fillRect((int) topBox.getBounds2D().getX(), (int) topBox.getBounds2D().getY(), 
		    		(int) topBox.getBounds2D().getWidth(), (int) topBox.getBounds2D().getHeight());
		}
	    
	    if (charging) {
	    	g2d.setTransform(at);
	    	g2d.setColor(new Color(255, 200, 0));
			int x, y, diam, rad;
	    	switch (spriteMotion) {
	    		case START:
	    			break;
	    		case WALKLEFT:
	    			x = -10; y = 20 / size; diam = (int) ((beamSize / size + Math.random() * 3)); rad = diam / 2;
	    	    	g2d.fillOval(x - rad, y - rad, diam, diam);
	    	    	break;
	    		case WALKRIGHT:
	    			x = w / size; y = 20 / size; diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
	    	    	g2d.fillOval(x - rad, y - rad, diam, diam);
	    	    	break;
	    		case STANDLEFT:
	    			x = -10; y = 20 / size; diam = (int) ((beamSize / size + Math.random() * 3)); rad = diam / 2;
	    	    	g2d.fillOval(x - rad, y - rad, diam, diam);
	    	    	break;
	    		case STANDRIGHT:
	    			x = w / size; y = 20 / size; diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
	    	    	g2d.fillOval(x - rad, y - rad, diam, diam);
	    	    	break;
	    	}
	    }
	    
		move();
	}
	
	@Override
	public void move() {
		
		if (charging && beamSize < 30)
			beamSize += 0.05;
		
		dx = Math.pow(instance.getAxis1()[3], 3);
		//System.out.println(instance.getAxis1()[3]);
		
		checkCollision();
		
		x += dx;
		y += dy;
		
		updateHitBoxes();
		
		
		if (instance.getButt1()[2]) {
			jump();
			//System.out.println("jump");
		}
		
		fall();
		
		updateBeams();
		
	}
	
	private void updateSprite() {
		SpriteMotion last = spriteMotion;
		
		if (dx < 0 && isOnGround)
			spriteMotion = spriteMotion.WALKLEFT;
		else if (dx > 0 && isOnGround)
			spriteMotion = spriteMotion.WALKRIGHT;
		
		
		switch (spriteMotion) {
			case START:
				animation = Constants.samStart;
				break;
			case WALKLEFT:
				animation = Constants.samWalkLeft;
				break;
			case WALKRIGHT:
				animation = Constants.samStart;
				break;
		}
		
		if (last != spriteMotion)
			animation.restart();
		else
			animation.update();
		
	}
	
	public void jump() {
		if (instance.getRoomBounds().intersects(landBox.getBounds2D()))
			dy = -6;
	}
	
	public void fall() {
		if(!instance.getRoomBounds().intersects(landBox.getBounds2D()) && !instance.getRoomBounds().intersects(topBox.getBounds2D())) {
			dy += Constants.GRAVITY_ACCEL;
			isOnGround = false;
		} else if (instance.getRoomBounds().intersects(topBox.getBounds2D())) {
			dy = 0.15;
		} else /*player is on the ground*/ {
			dy = 0;
			isOnGround = true;
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
					if (e.getY() > landBox.getBounds2D().getY()) {
						dy = -0.1;
					}
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
		w = animation.getSprite().getWidth() * size;
		h = animation.getSprite().getHeight() * size;
		Rectangle hitBoxRect = new Rectangle((int) x, (int) y + 5, w, h - 10);
		hitBox = new Area(hitBoxRect);
		Rectangle landBoxRect = new Rectangle((int) (x + 2), (int) (h + y - 5), w - 4, 5);
		landBox = new Area(landBoxRect);
		Rectangle topBoxRect = new Rectangle((int) (x + 2), (int) y, w - 4, 5);
		topBox = new Area(topBoxRect);
	}
	
	public void updateInstance(Runner in) {
		instance = in;
		for (Beam b : beams)
			b.updateInstance(in);
	}

	public void fire() {
		switch (spriteMotion) {
			case START:
				break;
			case WALKLEFT:
				beams.add(new Beam(0, -3, beamSize, x - 10, y + 20));
				break;
			case WALKRIGHT:
				beams.add(new Beam(0, 3, beamSize, x + w, y + 20));
				break;
		}
		beams.get(beams.size() - 1).updateInstance(instance);
	}
	
	public void charge() {
		charging = true;
	}
	
	public void resetCharge() {
		charging = false;
		beamSize = 20;
	}
	
	public void updateBeams() {
		for (int i = 0; i < beams.size(); i++) {
			beams.get(i).updateInstance(instance);
			if (!beams.get(i).isAlive())
				beams.remove(beams.get(i));
		}
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
	
	public ArrayList<Beam> getBeams() {
		return beams;
	}
	
	public Graphics2D getGraphics() {
		return g2d;
	}
	
}