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

import enemy.AttackBox;
import entity.Being;
import main.Constants;
import main.Runner;
import sprite.Animation;
import sprite.SpriteSheet;
import tiles.Tile;
import weapon.Beam;

public class Player extends Being {
	
	private double lastX, lastY;
	
	Animation animation = Constants.samStart;
	
	Area leftBox, rightBox;
	
	int size = 3;

	private boolean stopL = false, stopR = false;
	private boolean isOnGround = false, firing;
	
	Runner instance;
	
	ArrayList<Beam> oldBeams = new ArrayList<Beam>();
	ArrayList<Beam> beams = new ArrayList<Beam>();
	private boolean charging = false;
	private boolean facingLeft;
	private double beamSize = 5.0;
	private double speed = 5.0;
	
	private enum SpriteMotion { //34 states
		START, MORPH, JUMP_SPIN_LEFT, JUMP_SPIN_RIGHT,  //no direction/etc.

		STAND_LEFT, WALK_LEFT, JUMP_LEFT, CROUCH_LEFT, //left
		STAND_RIGHT, WALK_RIGHT, JUMP_RIGHT, CROUCH_RIGHT, //right

		AIM_UP_L, JUMP_UP_L, //up (facing left)
		AIM_UP_R, JUMP_UP_R, //up (facing right)
		JUMP_DOWN_L, //down (facing left)
		JUMP_DOWN_R, //down (facing right)

		AIM_UP_LEFT, WALK_UP_LEFT, JUMP_UP_LEFT, CROUCH_UP_LEFT, //up-left
		AIM_UP_RIGHT, WALK_UP_RIGHT, JUMP_UP_RIGHT, CROUCH_UP_RIGHT, //up-right
		AIM_DOWN_LEFT, WALK_DOWN_LEFT, JUMP_DOWN_LEFT, CROUCH_DOWN_LEFT, //down-left
		AIM_DOWN_RIGHT, WALK_DOWN_RIGHT, JUMP_DOWN_RIGHT, CROUCH_DOWN_RIGHT, //down-right
	};
	
	SpriteMotion spriteMotion;
	
	public Player(Runner in) {
		instance = in;
		
		spriteMotion = spriteMotion.START;
		
		animation.start();
		
		x = 1280 / 2 + 9;
		y = 720 / 2;
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
	public void draw(Graphics2D g) {
		
		AffineTransform at = new AffineTransform();
	    at.translate(x, y);
	    at.scale(size, size);
	    g.transform(at);
	    
	    updateSprite();
	    g.drawImage(animation.getSprite(), 0, 0, null);
	    
		if (Constants.SHOWHITBOXES) {
		    g.setColor(new Color(255, 255, 255, 175));
		    g.fill(hitBox);
		    g.setColor(new Color(0, 255, 255, 175));
		    g.fill(topBox);
		    g.fill(landBox);
		}
	    
	    if (charging && beamSize > 7) {
	    	g.setColor(new Color(255, 200, 0));
			int x, y, diam, rad;
			double rando;
	    	switch (spriteMotion) {
	    	/*//34 states
			START, MORPH, JUMP_SPIN_LEFT, JUMP_SPIN_RIGHT,  //no direction/etc.

			STAND_LEFT, WALK_LEFT, JUMP_LEFT, CROUCH_LEFT, //left
			STAND_RIGHT, WALK_RIGHT, JUMP_RIGHT, CROUCH_RIGHT, //right

			AIM_UP_L, JUMP_UP_L, //up (facing left)
			AIM_UP_R, JUMP_UP_R, //up (facing right)
			JUMP_DOWN_L, //down (facing left)
			JUMP_DOWN_R, //down (facing right)

			AIM_UP_LEFT, WALK_UP_LEFT, JUMP_UP_LEFT, CROUCH_UP_LEFT, //up-left
			AIM_UP_RIGHT, WALK_UP_RIGHT, JUMP_UP_RIGHT, CROUCH_UP_RIGHT, //up-right
			AIM_DOWN_LEFT, WALK_DOWN_LEFT, JUMP_DOWN_LEFT, CROUCH_DOWN_LEFT, //down-left
			AIM_DOWN_RIGHT, WALK_DOWN_RIGHT, JUMP_DOWN_RIGHT, CROUCH_DOWN_RIGHT, //down-right*/
	    		case START:
	    			break;
	    		case MORPH:
	    			break;
	    		case STAND_LEFT:
	    			x = -10; y = 20 / size + 5; diam = (int) ((beamSize / size + Math.random() * 3)); rad = diam / 2;
	    	    	g.fillOval(x - rad, y - rad, diam, diam);
	    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
	    	    	rando = Math.random() * 2 + 1;
	    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
	    	    	break;
	    		case WALK_LEFT:
	    			x = -5; y = 20 / size + 5; diam = (int) ((beamSize / size + Math.random() * 3)); rad = diam / 2;
	    	    	g.fillOval(x - rad, y - rad, diam, diam);
	    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
	    	    	rando = Math.random() * 2 + 1;
	    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
	    		case JUMP_LEFT:
	    			x = -10; y = 20 / size + 5; diam = (int) ((beamSize / size + Math.random() * 3)); rad = diam / 2;
	    	    	g.fillOval(x - rad, y - rad, diam, diam);
	    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
	    	    	rando = Math.random() * 2 + 1;
	    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
	    	    	break;
	    		case CROUCH_LEFT:
	    			x = -10; y = 20 / size + 5; diam = (int) ((beamSize / size + Math.random() * 3)); rad = diam / 2;
	    	    	g.fillOval(x - rad, y - rad, diam, diam);
	    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
	    	    	rando = Math.random() * 2 + 1;
	    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
	    	    	break;
	    		case STAND_RIGHT:
	    			x = w / size; y = 20 / size + 5; diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
	    	    	g.fillOval(x - rad, y - rad, diam, diam);
	    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
	    	    	rando = Math.random() * 2 + 1;
	    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
	    	    	break;
	    		case WALK_RIGHT:
	    			x = w / size + 10; y = 20 / size + 5; diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
	    	    	g.fillOval(x - rad, y - rad, diam, diam);
	    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
	    	    	rando = Math.random() * 2 + 1;
	    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));      
	    	    	break;
	    		case JUMP_RIGHT:
	    			x = w / size; y = 20 / size + 5; diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
	    	    	g.fillOval(x - rad, y - rad, diam, diam);
	    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
	    	    	rando = Math.random() * 2 + 1;
	    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
	    	    	break;
	    		case CROUCH_RIGHT:
	    			x = w / size; y = 20 / size + 5; diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
	    	    	g.fillOval(x - rad, y - rad, diam, diam);
	    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
	    	    	rando = Math.random() * 2 + 1;
	    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
	    	    	break;
	    		case AIM_UP_L:
<<<<<<< HEAD
	    			x = w / size - 15; y = 20 / size - 9; diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
	    	    	g2d.fillOval(x - rad, y - rad, diam, diam);
	    	    	g2d.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
=======
	    			x = -10; y = 20 / size + 5; diam = (int) ((beamSize / size + Math.random() * 3)); rad = diam / 2;
	    	    	g.fillOval(x - rad, y - rad, diam, diam);
	    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
>>>>>>> origin/master
	    	    	rando = Math.random() * 2 + 1;
	    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
	    	    	break;
	    		case JUMP_UP_L:
<<<<<<< HEAD
	    			x = w / size - 15; y = 20 / size - 9; diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
	    	    	g2d.fillOval(x - rad, y - rad, diam, diam);
	    	    	g2d.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
=======
	    			x = -10; y = 20 / size + 5; diam = (int) ((beamSize / size + Math.random() * 3)); rad = diam / 2;
	    	    	g.fillOval(x - rad, y - rad, diam, diam);
	    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
>>>>>>> origin/master
	    	    	rando = Math.random() * 2 + 1;
	    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
	    	    	break;
	    		case AIM_UP_R:
<<<<<<< HEAD
	    			x = w / size - 7; y = 20 / size - 9; diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
	    	    	g2d.fillOval(x - rad, y - rad, diam, diam);
	    	    	g2d.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
=======
	    			x = w / size; y = 20 / size + 5; diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
	    	    	g.fillOval(x - rad, y - rad, diam, diam);
	    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
>>>>>>> origin/master
	    	    	rando = Math.random() * 2 + 1;
	    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
	    	    	break;
	    		case JUMP_UP_R:
<<<<<<< HEAD
	    			x = w / size - 7; y = 20 / size - 9; diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
	    	    	g2d.fillOval(x - rad, y - rad, diam, diam);
	    	    	g2d.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
=======
	    			x = w / size; y = 20 / size + 5; diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
	    	    	g.fillOval(x - rad, y - rad, diam, diam);
	    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
>>>>>>> origin/master
	    	    	rando = Math.random() * 2 + 1;
	    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
	    	    	break;
	    		
	    	}
	    }
	    
		move();
	}
	
	@Override
	public void move() {
		
		if (charging && beamSize < 30)
			beamSize += 0.5;
		
		dx = Math.pow(instance.getAxis1()[3], 3) * speed;
		if(dx < 0)
			facingLeft = true;
		else if(dx > 0)
			facingLeft = false;

		//System.out.println(instance.getAxis1()[3]);
		
		lastX = x;
		lastY = y;
		
		checkCollision();
		
		x += dx;
		y += dy;
		
		updateHitBoxes();
		
		
		if (instance.getButt1()[2]) {
			jump();
		}
		
		fall();
		
		updateBeams();
		
	}
	
	private void updateSprite() {
		SpriteMotion last = spriteMotion;
		
		if (dx < 0 && isOnGround)
			spriteMotion = SpriteMotion.WALK_LEFT;
		else if (dx > 0 && isOnGround)
			spriteMotion = SpriteMotion.WALK_RIGHT;
		else if (instance.getAxis1()[2] <= -0.9 && dx == 0 && isOnGround) {
			switch (last) {
				default:
					break;
				case WALK_LEFT:
					spriteMotion = SpriteMotion.AIM_UP_L;
					break;
				case WALK_RIGHT:
					spriteMotion = SpriteMotion.AIM_UP_R;
					break;
			}
		} else if (instance.getAxis1()[2] == 0 && dx == 0 && isOnGround) {
			switch(last) {
				default:
					break;
				case AIM_UP_L:
					spriteMotion = SpriteMotion.WALK_LEFT;
					break;
				case AIM_UP_R:
					spriteMotion = SpriteMotion.WALK_RIGHT;
					break;
			}
		}
		
		
		switch (spriteMotion) {
		/*//34 states
		START, MORPH, JUMP_SPIN_LEFT, JUMP_SPIN_RIGHT,  //no direction/etc.

		STAND_LEFT, WALK_LEFT, JUMP_LEFT, CROUCH_LEFT, //left
		STAND_RIGHT, WALK_RIGHT, JUMP_RIGHT, CROUCH_RIGHT, //right

		AIM_UP_L, JUMP_UP_L, //up (facing left)
		AIM_UP_R, JUMP_UP_R, //up (facing right)
		JUMP_DOWN_L, //down (facing left)
		JUMP_DOWN_R, //down (facing right)

		AIM_UP_LEFT, WALK_UP_LEFT, JUMP_UP_LEFT, CROUCH_UP_LEFT, //up-left
		AIM_UP_RIGHT, WALK_UP_RIGHT, JUMP_UP_RIGHT, CROUCH_UP_RIGHT, //up-right
		AIM_DOWN_LEFT, WALK_DOWN_LEFT, JUMP_DOWN_LEFT, CROUCH_DOWN_LEFT, //down-left
		AIM_DOWN_RIGHT, WALK_DOWN_RIGHT, JUMP_DOWN_RIGHT, CROUCH_DOWN_RIGHT, //down-right*/
			default:
				break;
			case START:
				animation = Constants.samStart;
				break;
//			case MORPH:
//				animation = Constants.samX;
//				break;
//			case JUMP_SPIN_LEFT:
//				animation = Constants.samX;
//				break;
//			case JUMP_SPIN_RIGHT:
//				animation = Constants.samX;
//				break;
//			case STAND_LEFT:
//				animation = Constants.samX;
//				break;
			case WALK_LEFT:
				animation = Constants.samWalkLeft;
				break;
//			case JUMP_LEFT:
//				animation = Constants.samX;
//				break;
//			case CROUCH_LEFT:
//				animation = Constants.samX;
//				break;
//			case STAND_RIGHT:
//				animation = Constants.samX;
//				break;
			case WALK_RIGHT:
				animation = Constants.samWalkRight;
				break;
//			case JUMP_RIGHT:
//				animation = Constants.samX;
//				break;
//			case CROUCH_RIGHT:
//				animation = Constants.samX;
//				break;
			case AIM_UP_L:
				animation = Constants.samFireUpL;
				break;
//			case JUMP_UP_L:
//				animation = Constants.samX;
//				break;
			case AIM_UP_R:
				animation = Constants.samFireUpR;
				break;
//			case JUMP_DOWN_L:
//				animation = Constants.samX;
//				break;
//			case JUMP_DOWN_R:
//				animation = Constants.samX;
//				break;
//			case AIM_UP_LEFT:
//				animation = Constants.samX;
//				break;
//			case WALK_UP_LEFT:
//				animation = Constants.samX;
//				break;
//			case JUMP_UP_LEFT:
//				animation = Constants.samX;
//				break;
//			case CROUCH_UP_LEFT:
//				animation = Constants.samX;
//				break;
//			case AIM_UP_RIGHT:
//				animation = Constants.samX;
//				break;
//			case WALK_UP_RIGHT:
//				animation = Constants.samX;
//				break;
//			case JUMP_UP_RIGHT:
//				animation = Constants.samX;
//				break;
//			case CROUCH_UP_RIGHT:
//				animation = Constants.samX;
//				break;
//			case AIM_DOWN_LEFT:
//				animation = Constants.samX;
//				break;
//			case WALK_DOWN_LEFT:
//				animation = Constants.samX;
//				break;
//			case JUMP_DOWN_LEFT:
//				animation = Constants.samX;
//				break;
//			case CROUCH_DOWN_LEFT:
//				animation = Constants.samX;
//				break;
//			case AIM_DOWN_RIGHT:
//				animation = Constants.samX;
//				break;
//			case WALK_DOWN_RIGHT:
//				animation = Constants.samX;
//				break;
//			case JUMP_DOWN_RIGHT:
//				animation = Constants.samX;
//				break;
//			case CROUCH_DOWN_RIGHT:
//				animation = Constants.samX;
//				break;
		}
		
		if (last != spriteMotion)
			animation.restart();
		else
			animation.update();
		
	}
	
	public void jump() {
		if (instance.getRoomBounds().intersects(landBox.getBounds2D()))
			dy = Constants.JUMP_SPEED;
	}

	
	public void fall() {
		if(!instance.getRoomBounds().intersects(landBox.getBounds2D()) && !instance.getRoomBounds().intersects(topBox.getBounds2D())) {
			if(dy < Constants.TERMINAL_VELOCITY) {
				dy += Constants.GRAVITY_ACCEL;
			}
			isOnGround = false;
		} else if (instance.getRoomBounds().intersects(topBox.getBounds2D())) {
			dy = Constants.BONK_SPEED;
		} else /*player is on the ground*/ {
			dy = 0;
			isOnGround = true;
		}
	}

	public void damage(AttackBox attackBox) {
		health -= attackBox.getDamage();
		if(facingLeft)
			dx = Constants.KNOCKBACK_SPEED;
		else
			dx = -Constants.KNOCKBACK_SPEED;
	}

	
	public void checkCollision() {
		ArrayList<Tile> tiles = instance.getRoom().getIntersectingTiles(this);
		stopL = false;
		stopR = false;
		if (tiles.size() > 0) {
			for (Tile e : tiles) {
				if (hitBox.intersects(e.getHitBox().getBounds2D())) {
					if (e.getX() + e.getW() < this.x)
						stopL = true;
					if (e.getX() > this.x + w)
						stopR = true;
					if (e.getY() < landBox.getBounds2D().getY() && landBox.intersects(e.getHitBox().getBounds2D())) {
						y = e.getY() - h;
					}
				}
			}
		}
		
		if (stopL && dx < 0)
			dx = 0;
		else if (stopR && dx > 0)
			dx = 0;
	}
	
	private void updateHitBoxes() {
		w = animation.getSprite().getWidth() * size;
		h = animation.getSprite().getHeight() * size;
		Rectangle hitBoxRect = new Rectangle((int) x, (int) y + 5, w, h - 10);
		hitBox = new Area(hitBoxRect);
		Rectangle landBoxRect = new Rectangle((int) (x + 3), (int) (h + y - 5), w - 6, 5);
		landBox = new Area(landBoxRect);
		Rectangle topBoxRect = new Rectangle((int) (x + 3), (int) y, w - 6, 5);
		topBox = new Area(topBoxRect);
	}
	
	public void updateInstance(Runner in) {
		instance = in;
		for (Beam b : beams)
			b.updateInstance(in);
	}

	public void fire() {
		
		oldBeams = beams;
		
		if (beamSize < Constants.MAX_BEAM_SIZE)
			beamSize = Constants.MAX_BEAM_SIZE;
		
		switch (spriteMotion) {
		/*//34 states
		START, MORPH, JUMP_SPIN_LEFT, JUMP_SPIN_RIGHT,  //no direction/etc.

		STAND_LEFT, WALK_LEFT, JUMP_LEFT, CROUCH_LEFT, //left
		STAND_RIGHT, WALK_RIGHT, JUMP_RIGHT, CROUCH_RIGHT, //right

		AIM_UP_L, JUMP_UP_L, //up (facing left)
		AIM_UP_R, JUMP_UP_R, //up (facing right)
		JUMP_DOWN_L, //down (facing left)
		JUMP_DOWN_R, //down (facing right)

		AIM_UP_LEFT, WALK_UP_LEFT, JUMP_UP_LEFT, CROUCH_UP_LEFT, //up-left
		AIM_UP_RIGHT, WALK_UP_RIGHT, JUMP_UP_RIGHT, CROUCH_UP_RIGHT, //up-right
		AIM_DOWN_LEFT, WALK_DOWN_LEFT, JUMP_DOWN_LEFT, CROUCH_DOWN_LEFT, //down-left
		AIM_DOWN_RIGHT, WALK_DOWN_RIGHT, JUMP_DOWN_RIGHT, CROUCH_DOWN_RIGHT, //down-right*/
			case START:
				break;
			case MORPH:
				break;
			case JUMP_SPIN_LEFT:
				beams.add(new Beam(0, -Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				spriteMotion = SpriteMotion.JUMP_LEFT;
				break;
			case JUMP_SPIN_RIGHT:
				beams.add(new Beam(0, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				spriteMotion = SpriteMotion.JUMP_RIGHT;
				break;
			case STAND_LEFT:
				beams.add(new Beam(0, -Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case STAND_RIGHT:
				beams.add(new Beam(0, Constants.BEAM_SPEED, beamSize, x + w, y + 20));
				break;
			case WALK_LEFT:
				beams.add(new Beam(0, -Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case WALK_RIGHT:
				beams.add(new Beam(0, Constants.BEAM_SPEED, beamSize, x + w, y + 20));
				break;
			case CROUCH_LEFT:
				beams.add(new Beam(0, -Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case CROUCH_RIGHT:
				beams.add(new Beam(0, Constants.BEAM_SPEED, beamSize, x + w, y + 20));
				break;
			case JUMP_LEFT:
				beams.add(new Beam(0, -Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case JUMP_RIGHT:
				beams.add(new Beam(0, Constants.BEAM_SPEED, beamSize, x + w, y + 20));
				break;
			case AIM_UP_L:
				beams.add(new Beam(90, Constants.BEAM_SPEED, beamSize, x + 31, y - 26));
				break;
			case AIM_UP_R:
				beams.add(new Beam(90, Constants.BEAM_SPEED, beamSize, x + 55, y - 26));
				break;
			case JUMP_UP_L:
				beams.add(new Beam(90, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case JUMP_UP_R:
				beams.add(new Beam(90, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case JUMP_DOWN_L:
				beams.add(new Beam(90, -Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case JUMP_DOWN_R:
				beams.add(new Beam(90, -Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case AIM_UP_LEFT:
				beams.add(new Beam(135, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case WALK_UP_LEFT:
				beams.add(new Beam(135, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case JUMP_UP_LEFT:
				beams.add(new Beam(135, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case CROUCH_UP_LEFT:
				beams.add(new Beam(135, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case AIM_UP_RIGHT:
				beams.add(new Beam(45, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case WALK_UP_RIGHT:
				beams.add(new Beam(45, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case JUMP_UP_RIGHT:
				beams.add(new Beam(45, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case CROUCH_UP_RIGHT:
				beams.add(new Beam(45, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case AIM_DOWN_LEFT:
				beams.add(new Beam(225, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case WALK_DOWN_LEFT:
				beams.add(new Beam(225, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case JUMP_DOWN_LEFT:
				beams.add(new Beam(225, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case CROUCH_DOWN_LEFT:
				beams.add(new Beam(225, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case AIM_DOWN_RIGHT:
				beams.add(new Beam(315, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case WALK_DOWN_RIGHT:
				beams.add(new Beam(315, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case JUMP_DOWN_RIGHT:
				beams.add(new Beam(315, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case CROUCH_DOWN_RIGHT:
				beams.add(new Beam(315, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
		}
		
		beams.get( beams.size() - 1 ).updateInstance(instance);
	}
	
	public void charge() {
		charging = true;
	}
	
	public void resetCharge() {
		charging = false;
		beamSize = 5.0;
	}
	
	public void updateBeams() {
		for (int i = 0; i < beams.size(); i++) {
			beams.get(i).updateInstance(instance);
			if (!beams.get(i).isAlive())
				beams.remove(beams.get(i));
		}
	}
	
	public double getLastX() {
		return lastX;
	}
	
	public double getLastY() {
		return lastY;
	}
	public double getDx() {return dx;}

	public double getDy() {return dy;}
	
	public void setDx(double dx) {this.dx = dx;}

	public void setDy(double dy) {this.dy = dy;}
	
	public ArrayList<Beam> getBeams() {return oldBeams;}
}