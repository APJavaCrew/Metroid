package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.sun.glass.events.KeyEvent;

import enemy.AttackBox;
import entity.Being;
import main.Constants;
import main.Runner;
import sprite.Animation;
import sprite.SpriteSheet;
import tiles.Tile;
import weapon.Beam;
import weapon.Weapon;

public class Player extends Being {
	
	private double lastX, lastY;
	private double hurtX, hurtY;
	
	Animation animation = Constants.samStart;
	
	Area leftBox, rightBox, ascendBox;
	
	Clip chargeStart, chargeLoop;
	
	double size = 2.7;
	double w, h;

	private boolean stopL = false, stopR = false;
	private boolean isOnGround = false, firing;
	public boolean removingWeapon = false;
	
	Runner instance;
	
	ArrayList<Weapon> oldWeapons = new ArrayList<Weapon>();
	ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	private boolean charging = false;
	private boolean facingLeft;
	private boolean isHurt = false, isHurtStart = false;
	private double beamSize = 5.0;
	private double speed = 5.0;
	String beamType = "ice";
	
	private int hurtTimeout = 0;
	private int deathOpac = 0;
	
	private enum SpriteMotion { //34 states
		START, MORPH, JUMP_SPIN_LEFT, JUMP_SPIN_RIGHT,  //no direction/etc.

		AIM_LEFT, WALK_LEFT, JUMP_LEFT, CROUCH_LEFT, //left
		AIM_RIGHT, WALK_RIGHT, JUMP_RIGHT, CROUCH_RIGHT, //right

		AIM_UP_L, JUMP_UP_L, //up (facing left)
		AIM_UP_R, JUMP_UP_R, //up (facing right)
		JUMP_DOWN_L, //down (facing left)
		JUMP_DOWN_R, //down (facing right)

		AIM_UP_LEFT, WALK_UP_LEFT, JUMP_UP_LEFT, CROUCH_UP_LEFT, //up-left
		AIM_UP_RIGHT, WALK_UP_RIGHT, JUMP_UP_RIGHT, CROUCH_UP_RIGHT, //up-right
		AIM_DOWN_LEFT, WALK_DOWN_LEFT, JUMP_DOWN_LEFT, CROUCH_DOWN_LEFT, //down-left
		AIM_DOWN_RIGHT, WALK_DOWN_RIGHT, JUMP_DOWN_RIGHT, CROUCH_DOWN_RIGHT, //down-right
	}
	
	SpriteMotion spriteMotion;
	
	public Player(Runner in) {
		instance = in;
		
		spriteMotion = SpriteMotion.START;
		
		
		animation.start();
		
		x = 1280 / 2;
		y = 500;
		dx = 0;
		dy = 0;
		
		setHealth(99);
		
		Rectangle hitBoxRect = new Rectangle((int) x, (int) y, (int) (w * size), (int) (h * size)); //use this to translate the hitBox
		hitBox = new Area(hitBoxRect);
		Rectangle rightBoxRect = new Rectangle((int) (x + w - 5), (int) (y + 5), 5, (int) (h - 10));
		rightBox = new Area(rightBoxRect);
		Rectangle leftBoxRect = new Rectangle((int) x, (int) (y + 5), 5, (int) (h - 10));
		leftBox = new Area(leftBoxRect);
		Rectangle ascendBoxRect = new Rectangle((int) (x + w / 2 - 3), (int) (y + h - 10), 6, 5);
		ascendBox = new Area(ascendBoxRect);
		
		w = animation.getSprite().getWidth() * size;
		h = animation.getSprite().getHeight() * size;
		
		try {
			
			AudioInputStream chargeLStr = AudioSystem.getAudioInputStream(new File("Music/chargeLoop.wav"));
			chargeLoop = AudioSystem.getClip();
			chargeLoop.open(chargeLStr);
			
			AudioInputStream chargeSStr = AudioSystem.getAudioInputStream(new File("Music/chargeStart.wav"));
			chargeStart = AudioSystem.getClip();
			chargeStart.open(chargeSStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
//	public Player(double x, double y, double dx, double dy) {
//
//		spriteMotion = spriteMotion.START;
//
//		animation.start();
//		
//		this.x = x; 
//		this.y = y;
//		this.dx = dx;
//		this.dy = dy;
//		
//		w = animation.getSprite().getWidth() * size;
//		h = animation.getSprite().getHeight() * size;
//		
//		Rectangle hitBoxRect = new Rectangle((int) x, (int) y, w * size, h * size); //use this to translate the hitBox
//		hitBox = new Area(hitBoxRect);
//	}
	
	private void drawDeath(Graphics2D g) {
		g.setColor( new Color(0, 0, 0, deathOpac) );
		if (deathOpac < 255)
			deathOpac += 5;
		else
			isAlive = false;
		AffineTransform at = new AffineTransform();
		at.translate(instance.getCamera().getXOffset(), instance.getCamera().getYOffset());
		g.transform(at);
		g.fillRect(-instance.getWidth() / 2, -instance.getHeight() / 2, instance.getWidth(), instance.getHeight());
		
		move();
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		
		AffineTransform at = new AffineTransform();
	    at.translate(x, y);
	    at.scale(size, size);
	    g.transform(at);
	    if (health <= 0) {
	    	drawDeath(g);
	    } else {
		    updateSprite();
		    if (hurtTimeout > 5 || !isHurt)
		    	g.drawImage(animation.getSprite(), 0, 0, null);
		    
			if (Constants.SHOWHITBOXES) {
				at = new AffineTransform();
				at.translate(0, 0);
				g.transform(at);
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
	
				AIM_LEFT, WALK_LEFT, JUMP_LEFT, CROUCH_LEFT, //left
				AIM_RIGHT, WALK_RIGHT, JUMP_RIGHT, CROUCH_RIGHT, //right
	
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
		    		case AIM_LEFT:
		    			x = (int) (w / size - 25); y = (int) (20 / size + 1); diam = (int) ((beamSize / size + Math.random() * 3)); rad = diam / 2;
		    	    	g.fillOval(x - rad, y - rad, diam, diam);
		    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
		    	    	rando = Math.random() * 2 + 1;
		    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
		    	    	break;
		    		case WALK_LEFT:
		    			x = (int) (w / size - 35); y = (int) (20 / size + 1); diam = (int) ((beamSize / size + Math.random() * 3)); rad = diam / 2;
		    	    	g.fillOval(x - rad, y - rad, diam, diam);
		    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
		    	    	rando = Math.random() * 2 + 1;
		    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
		    		case JUMP_LEFT:
		    			x = (int) (w / size + 10); y = (int) (20 / size + 1); diam = (int) ((beamSize / size + Math.random() * 3)); rad = diam / 2;
		    	    	g.fillOval(x - rad, y - rad, diam, diam);
		    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
		    	    	rando = Math.random() * 2 + 1;
		    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
		    	    	break;
		    		case CROUCH_LEFT:
		    			x = (int) (w / size - 25); y = (int) (20 / size + 5); diam = (int) ((beamSize / size + Math.random() * 3)); rad = diam / 2;
		    	    	g.fillOval(x - rad, y - rad, diam, diam);
		    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
		    	    	rando = Math.random() * 2 + 1;
		    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
		    	    	break;
		    		case AIM_RIGHT:
		    			x = (int) (w / size + 10); y = (int) (20 / size + 1); diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
		    	    	g.fillOval(x - rad, y - rad, diam, diam);
		    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
		    	    	rando = Math.random() * 2 + 1;
		    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
		    	    	break;
		    		case WALK_RIGHT:
		    			x = (int) (w / size); y = (int) (20 / size + 1); diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
		    	    	g.fillOval(x - rad, y - rad, diam, diam);
		    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
		    	    	rando = Math.random() * 2 + 1;
		    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));      
		    	    	break;
		    		case JUMP_RIGHT:
		    			x = (int) (w / size); y = (int) (20 / size + 1); diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
		    	    	g.fillOval(x - rad, y - rad, diam, diam);
		    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
		    	    	rando = Math.random() * 2 + 1;
		    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
		    	    	break;
		    		case CROUCH_RIGHT:
		    			x = (int) (w / size); y = (int) (20 / size + 5); diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
		    	    	g.fillOval(x - rad, y - rad, diam, diam);
		    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
		    	    	rando = Math.random() * 2 + 1;
		    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
		    	    	break;
		    		case AIM_UP_L:
		    			x = (int) (w / size - 15); y = (int) (20 / size - 9); diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
		    	    	g.fillOval(x - rad, y - rad, diam, diam);
		    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
		    	    	rando = Math.random() * 2 + 1;
		    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
		    	    	break;
		    		case JUMP_UP_L:
		    			x = (int) (w / size - 15); y = (int) (20 / size - 9); diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
		    	    	g.fillOval(x - rad, y - rad, diam, diam);
		    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
		    	    	rando = Math.random() * 2 + 1;
		    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
		    	    	break;
		    		case AIM_UP_R:
		    			x = (int) (w / size - 7); y = (int) (20 / size - 9); diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
		    	    	g.fillOval(x - rad, y - rad, diam, diam);
		    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
		    	    	rando = Math.random() * 2 + 1;
		    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
		    	    	break;
		    		case JUMP_UP_R:
		    			x = (int) (w / size - 7); y = (int) (20 / size - 9); diam = (int) (beamSize / size + Math.random() * 3); rad = diam / 2;
		    	    	g.fillOval(x - rad, y - rad, diam, diam);
		    	    	g.setColor( new Color( 255, 150, 0, (int) (Math.random() * 70) ) );
		    	    	rando = Math.random() * 2 + 1;
		    	    	g.fillOval(x - (int) (rad / rando), y - (int) (rad / rando), (int) (diam / rando), (int) (diam / rando));
		    	    	break;
		    		
		    	}
		    }
		    
			move();
	    }
	}
	
	@Override
	public void move() {
		
		if (charging && beamSize < 30)
			beamSize += 0.5;
		
		if (charging && beamSize == 8.5) {
			chargeStart.setFramePosition(0);
			chargeStart.start();
		}
		
		if (charging && beamSize >= 30)
			chargeLoop.loop(-1);
		
		dx = Math.pow(instance.getAxis1()[3], 3) * speed;
		if(dx < 0)
			facingLeft = true;
		else if(dx > 0)
			facingLeft = false;
		
		lastX = x;
		lastY = y;
		
		checkCollision();
		
		if (isHurtStart) {
			dx += (x - hurtX) * 0.5;
			if (!instance.getRoomBounds().intersects(landBox.getBounds2D()))
				dy = (y - hurtY) * 0.075;
			else
				dy = -7;
		}
		
		if (health <= 0) {
			dx = 0;
			dy = 0;
		}
		
		x += dx;
		y += dy;
		
		updateHitBoxes();
		
		checkHurt();
		
		
		if (instance.getButt1()[2]) {
			jump();
		}
		
		fall();
		
		updateWeapons();
		
	}
	
	private void updateSprite() {
		SpriteMotion last = spriteMotion;
		
		if (dx < 0 && isOnGround)
			switch(last) {
				default:
					spriteMotion = SpriteMotion.WALK_LEFT;
					break;
				case CROUCH_RIGHT:
					spriteMotion = SpriteMotion.CROUCH_LEFT;
					break;
				case CROUCH_UP_RIGHT:
					spriteMotion = SpriteMotion.CROUCH_UP_LEFT;
					break;
				case CROUCH_DOWN_RIGHT:
					spriteMotion = SpriteMotion.CROUCH_DOWN_LEFT;
					break;
			}
		else if (dx > 0 && isOnGround)
			switch(last) {
			default:
				spriteMotion = SpriteMotion.WALK_RIGHT;
				break;
			case CROUCH_LEFT:
				spriteMotion = SpriteMotion.CROUCH_RIGHT;
				break;
			case CROUCH_UP_LEFT:
				spriteMotion = SpriteMotion.CROUCH_UP_RIGHT;
				break;
			case CROUCH_DOWN_LEFT:
				spriteMotion = SpriteMotion.CROUCH_DOWN_RIGHT;
				break;
		}
		else if (instance.getAxis1()[2] <= -0.9 && dx == 0 && isOnGround) {
			switch (last) {//add crouch states > aim l/r
				default:
					break;
				case WALK_LEFT:
					spriteMotion = SpriteMotion.AIM_UP_L;
					break;
				case WALK_RIGHT:
					spriteMotion = SpriteMotion.AIM_UP_R;
					break;
				case AIM_LEFT:
					spriteMotion = SpriteMotion.AIM_UP_L;
					break;
				case AIM_RIGHT:
					spriteMotion = SpriteMotion.AIM_UP_R;
					break;
				case MORPH:
					if(facingLeft) {
						spriteMotion = SpriteMotion.CROUCH_LEFT;
						break;
					}
					else {
						spriteMotion = SpriteMotion.CROUCH_RIGHT;
						break;
					}
				case CROUCH_LEFT:
					spriteMotion = SpriteMotion.AIM_LEFT;
					break;
				case CROUCH_RIGHT:
					spriteMotion = SpriteMotion.AIM_RIGHT;
					break;
				case CROUCH_UP_LEFT:
					spriteMotion = SpriteMotion.AIM_LEFT;
					break;
				case CROUCH_UP_RIGHT:
					spriteMotion = SpriteMotion.AIM_RIGHT;
					break;
				case CROUCH_DOWN_LEFT:
					spriteMotion = SpriteMotion.AIM_LEFT;
					break;
				case CROUCH_DOWN_RIGHT:
					spriteMotion = SpriteMotion.AIM_RIGHT;
					break;
					
			}
		} else if (instance.getAxis1()[2] == 0  && dx == 0 && isOnGround) {
			switch(last) {
				default:
					break;
				case AIM_UP_L:
					spriteMotion = SpriteMotion.AIM_LEFT;
					break;
				case AIM_UP_R:
					spriteMotion = SpriteMotion.AIM_RIGHT;
					break;
				case WALK_LEFT:
					spriteMotion = SpriteMotion.AIM_LEFT;
					break;
				case WALK_RIGHT:
					spriteMotion = SpriteMotion.AIM_RIGHT;
					break;
					
			}
		} else if(instance.getAxis1()[2] >= 0.9 && dx == 0 && isOnGround) {
			switch(last) {
				default:
					break;
				case WALK_LEFT:
					spriteMotion = SpriteMotion.CROUCH_LEFT;
					break;
				case WALK_RIGHT:
					spriteMotion = SpriteMotion.CROUCH_RIGHT;
					break;
				case AIM_LEFT:
					spriteMotion = SpriteMotion.CROUCH_LEFT;
					break;
				case AIM_RIGHT:
					spriteMotion = SpriteMotion.CROUCH_RIGHT;
					break;
				case CROUCH_LEFT:
					spriteMotion = SpriteMotion.MORPH;
					break;
				case CROUCH_RIGHT:
					spriteMotion = SpriteMotion.MORPH;
					break;
				case CROUCH_UP_LEFT:
					spriteMotion = SpriteMotion.MORPH;
					break;
				case CROUCH_UP_RIGHT:
					spriteMotion = SpriteMotion.MORPH;
					break;
				case CROUCH_DOWN_LEFT:
					spriteMotion = SpriteMotion.MORPH;
					break;
				case CROUCH_DOWN_RIGHT:
					spriteMotion = SpriteMotion.MORPH;
					break;
			}
			
		}
		if(instance.isrPressed()) {
			switch(last) {
				default:
					break;
				case AIM_LEFT:
					spriteMotion = SpriteMotion.AIM_UP_LEFT;
					break;
				case WALK_LEFT:
					spriteMotion = SpriteMotion.WALK_UP_LEFT;
					break;
				case JUMP_LEFT:
					spriteMotion = SpriteMotion.JUMP_UP_LEFT;
					break;
				case CROUCH_LEFT:
					spriteMotion = SpriteMotion.CROUCH_UP_LEFT;
					break;
				case AIM_RIGHT:
					spriteMotion = SpriteMotion.AIM_UP_RIGHT;
					break;
				case WALK_RIGHT:
					spriteMotion = SpriteMotion.WALK_UP_RIGHT;
					break;
				case JUMP_RIGHT:
					spriteMotion = SpriteMotion.JUMP_UP_RIGHT;
					break;
				case CROUCH_RIGHT:
					spriteMotion = SpriteMotion.CROUCH_UP_RIGHT;
					break;
			}
		}
		else if(instance.islPressed()) {
			switch(last) {
				default:
					break;
				case AIM_LEFT:
					spriteMotion = SpriteMotion.AIM_DOWN_LEFT;
					break;
				case WALK_LEFT:
					spriteMotion = SpriteMotion.WALK_DOWN_LEFT;
					break;
				case JUMP_LEFT:
					spriteMotion = SpriteMotion.JUMP_DOWN_LEFT;
					break;
				case CROUCH_LEFT:
					spriteMotion = SpriteMotion.CROUCH_DOWN_LEFT;
					break;
				case AIM_RIGHT:
					spriteMotion = SpriteMotion.AIM_DOWN_RIGHT;
					break;
				case WALK_RIGHT:
					spriteMotion = SpriteMotion.WALK_DOWN_RIGHT;
					break;
				case JUMP_RIGHT:
					spriteMotion = SpriteMotion.JUMP_DOWN_RIGHT;
					break;
				case CROUCH_RIGHT:
					spriteMotion = SpriteMotion.CROUCH_DOWN_RIGHT;
					break;
			}
		}
		
		
		switch (spriteMotion) {
		/*//34 states
		START, MORPH, JUMP_SPIN_LEFT, JUMP_SPIN_RIGHT,  //no direction/etc.

		AIM_LEFT, WALK_LEFT, JUMP_LEFT, CROUCH_LEFT, //left
		AIM_RIGHT, WALK_RIGHT, JUMP_RIGHT, CROUCH_RIGHT, //right

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
		case MORPH:
			animation = Constants.samMorph;
			break;
		case JUMP_SPIN_LEFT:
			animation = Constants.samJumpSpinLeft;
			break;
		case JUMP_SPIN_RIGHT:
			animation = Constants.samJumpSpinRight;
			break;
		case AIM_LEFT:
			animation = Constants.samAimLeft;
			break;
		case WALK_LEFT:
			animation = Constants.samWalkLeft;
			break;
		case JUMP_LEFT:
			animation = Constants.samJumpLeft;
			break;
		case CROUCH_LEFT:
			animation = Constants.samCrouchLeft;
			break;
		case AIM_RIGHT:
			animation = Constants.samAimRight;
			break;
		case WALK_RIGHT:
			animation = Constants.samWalkRight;
			break;
		case JUMP_RIGHT:
			animation = Constants.samJumpRight;
			break;
		case CROUCH_RIGHT:
			animation = Constants.samCrouchRight;
			break;
		case AIM_UP_L:
			animation = Constants.samFireUpL;
			break;
		case JUMP_UP_L:
			animation = Constants.samJumpUpL;
			break;
		case AIM_UP_R:
			animation = Constants.samFireUpR;
			break;
		case JUMP_UP_R:
			animation = Constants.samJumpUpR;
			break;
		case JUMP_DOWN_L:
			animation = Constants.samJumpDownL;
			break;
		case JUMP_DOWN_R:
			animation = Constants.samJumpDownR;
			break;
		case AIM_UP_LEFT:
			animation = Constants.samAimUpLeft;
			break;
//		case WALK_UP_LEFT:
//			animation = Constants.samWalkUpLeft;
//			break;
		case JUMP_UP_LEFT:
			animation = Constants.samJumpUpLeft;
			break;
		case CROUCH_UP_LEFT:
			animation = Constants.samCrouchUpLeft;
			break;
		case AIM_UP_RIGHT:
			animation = Constants.samAimUpRight;
			break;
//		case WALK_UP_RIGHT:
//			animation = Constants.samWalkUpRight;
//			break;
		case JUMP_UP_RIGHT:
			animation = Constants.samJumpUpRight;
			break;
		case CROUCH_UP_RIGHT:
			animation = Constants.samCrouchRight;
			break;
		case AIM_DOWN_LEFT:
			animation = Constants.samAimDownLeft;
			break;
//		case WALK_DOWN_LEFT:
//			animation = Constants.samWalkDownLeft;
//			break;
		case JUMP_DOWN_LEFT:
			animation = Constants.samJumpDownLeft;
			break;
		case CROUCH_DOWN_LEFT:
			animation = Constants.samCrouchDownLeft;
			break;
		case AIM_DOWN_RIGHT:
			animation = Constants.samAimDownRight;
			break;
//		case WALK_DOWN_RIGHT:
//			animation = Constants.samWalkDownRight;
//			break;
		case JUMP_DOWN_RIGHT:
			animation = Constants.samJumpDownRight;
			break;
		case CROUCH_DOWN_RIGHT:
			animation = Constants.samCrouchDownRight;
			break;
		}
		
		if (last != spriteMotion)
			animation.restart();
		else
			animation.update();
	}
		

	
	public void jump() {
		if (isOnGround)
			dy = Constants.JUMP_SPEED;
	}

	
	public void fall() {
		if( !instance.getRoomBounds().intersects(landBox.getBounds2D()) && 
				!instance.getEnemyManager().getFreezeBox().intersects(landBox.getBounds2D()) && 
				!instance.getRoomBounds().intersects(topBox.getBounds2D())) {
			if(dy < Constants.TERMINAL_VELOCITY) {
				dy += Constants.GRAVITY_ACCEL;
			}
			isOnGround = false;
		} else if ( ( instance.getRoomBounds().intersects(topBox.getBounds2D()) || 
				instance.getEnemyManager().getFreezeBox().intersects(topBox.getBounds2D())) && !isOnGround) {
			dy = Constants.BONK_SPEED;
		} else /*player is on the ground*/ {
			dy = 0;
			isOnGround = true;
		}
	}
	
	public void aimUp() {
		return;
	}
	
	public void aimDown() {
		return;
	}

	public void damage(AttackBox attackBox) {
		health -= attackBox.getDamage();
		if(facingLeft)
			dx = Constants.KNOCKBACK_SPEED;
		else
			dx = -Constants.KNOCKBACK_SPEED;
	}

	
	public void checkCollision() {
		stopL = false;
		stopR = false;
		
		if (instance.getRoomBounds().intersects(leftBox.getBounds2D()) || 
				instance.getEnemyManager().getFreezeBox().intersects(leftBox.getBounds2D()))
			stopL = true;
		
		if (instance.getRoomBounds().intersects(rightBox.getBounds2D()) ||
				instance.getEnemyManager().getFreezeBox().intersects(rightBox.getBounds2D()))
			stopR = true;
		
		if ( ( instance.getRoomBounds().intersects(ascendBox.getBounds2D()) ||
				instance.getEnemyManager().getFreezeBox().intersects(ascendBox.getBounds2D()) ) &&
				!instance.getRoomBounds().intersects(topBox.getBounds2D()))
			dy = -2;
		else if (stopL && dx < 0)
			dx = 0;
		else if (stopR && dx > 0)
			dx = 0;
	}
	
	private void updateHitBoxes() {
		w = animation.getSprite().getWidth() * size;
		h = animation.getSprite().getHeight() * size;
		
		Rectangle hitBoxRect = new Rectangle((int) x, (int) y + 5, (int) w, (int) h - 10);
		hitBox = new Area(hitBoxRect);
		Rectangle landBoxRect = new Rectangle((int) x + 5, (int) (h + y - 5), (int) w - 10, 6);
		landBox = new Area(landBoxRect);
		Rectangle topBoxRect = new Rectangle((int) x + 5, (int) y - 1, (int) w - 10, 6);
		topBox = new Area(topBoxRect);
		Rectangle rightBoxRect = new Rectangle((int) (x + w - 5), (int) y, 6, (int) h);
		rightBox = new Area(rightBoxRect);
		Rectangle leftBoxRect = new Rectangle((int) x, (int) y, 5, (int) h);
		leftBox = new Area(leftBoxRect);
		Rectangle ascendBoxRect = new Rectangle((int) (x + w / 2 - 3), (int) (y + h - 5), 6, 5);
		ascendBox = new Area(ascendBoxRect);
		
	}
	
	public void updateInstance(Runner in) {
		instance = in;
		for (Weapon w : weapons)
			w.updateInstance(in);
	}

	public void fire() {
		
		oldWeapons = weapons;
		
		if (beamSize < Constants.MAX_BEAM_SIZE)
			beamSize = Constants.MAX_BEAM_SIZE;
		
		
		
		switch (spriteMotion) {
		/*//34 states
		START, MORPH, JUMP_SPIN_LEFT, JUMP_SPIN_RIGHT,  //no direction/etc.

		AIM_LEFT, WALK_LEFT, JUMP_LEFT, CROUCH_LEFT, //left
		AIM_RIGHT, WALK_RIGHT, JUMP_RIGHT, CROUCH_RIGHT, //right

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
				weapons.add(new Beam(beamType, 0, -Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				spriteMotion = SpriteMotion.JUMP_LEFT;
				break;
			case JUMP_SPIN_RIGHT:
				weapons.add(new Beam(beamType, 0, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				spriteMotion = SpriteMotion.JUMP_RIGHT;
				break;
			case AIM_LEFT:
				weapons.add(new Beam(beamType, 0, -Constants.BEAM_SPEED, beamSize, x - 10, y + 14));
				break;
			case AIM_RIGHT:
				weapons.add(new Beam(beamType, 0, Constants.BEAM_SPEED, beamSize, x + w, y + 14));
				break;
			case WALK_LEFT:
				weapons.add(new Beam(beamType, 0, -Constants.BEAM_SPEED, beamSize, x - 10, y + 17));
				break;
			case WALK_RIGHT:
				weapons.add(new Beam(beamType, 0, Constants.BEAM_SPEED, beamSize, x + w, y + 17));
				break;
			case CROUCH_LEFT:
				weapons.add(new Beam(beamType, 0, -Constants.BEAM_SPEED, beamSize, x - 10, y + 21));
				break;
			case CROUCH_RIGHT:
				weapons.add(new Beam(beamType, 0, Constants.BEAM_SPEED, beamSize, x + w, y + 21));
				break;
			case JUMP_LEFT:
				weapons.add(new Beam(beamType, 0, -Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case JUMP_RIGHT:
				weapons.add(new Beam(beamType, 0, Constants.BEAM_SPEED, beamSize, x + w, y + 20));
				break;
			case AIM_UP_L:
				weapons.add(new Beam(beamType, 90, Constants.BEAM_SPEED, beamSize, x + 31, y - 26));
				break;
			case AIM_UP_R:
				weapons.add(new Beam(beamType, 90, Constants.BEAM_SPEED, beamSize, x + 55, y - 26));
				break;
			case JUMP_UP_L:
				weapons.add(new Beam(beamType, 90, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case JUMP_UP_R:
				weapons.add(new Beam(beamType, 90, Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case JUMP_DOWN_L:
				weapons.add(new Beam(beamType, 90, -Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case JUMP_DOWN_R:
				weapons.add(new Beam(beamType, 90, -Constants.BEAM_SPEED, beamSize, x - 10, y + 20));
				break;
			case AIM_UP_LEFT:
				weapons.add(new Beam(beamType, 235, -Constants.BEAM_SPEED, beamSize, x - 10, y + 15));
				break;
			case WALK_UP_LEFT:
				weapons.add(new Beam(beamType, 235, -Constants.BEAM_SPEED, beamSize, x - 10, y + 15));
				break;
			case JUMP_UP_LEFT:
				weapons.add(new Beam(beamType, 235, -Constants.BEAM_SPEED, beamSize, x - 10, y + 15));
				break;
			case CROUCH_UP_LEFT:
				weapons.add(new Beam(beamType, 235, -Constants.BEAM_SPEED, beamSize, x - 10, y + 15));
				break;
			case AIM_UP_RIGHT:
				weapons.add(new Beam(beamType, 115, Constants.BEAM_SPEED, beamSize, x + 70, y + 0));
				break;
			case WALK_UP_RIGHT:
				weapons.add(new Beam(beamType, 115, Constants.BEAM_SPEED, beamSize, x + 70, y + 0));
				break;
			case JUMP_UP_RIGHT:
				weapons.add(new Beam(beamType, 115, Constants.BEAM_SPEED, beamSize, x + 70, y + 0));
				break;
			case CROUCH_UP_RIGHT:
				weapons.add(new Beam(beamType, 115, Constants.BEAM_SPEED, beamSize, x + 70, y + 0));
				break;
			case AIM_DOWN_LEFT:
				weapons.add(new Beam(beamType, 115, -Constants.BEAM_SPEED, beamSize, x - 10, y + 70));
				break;
			case WALK_DOWN_LEFT:
				weapons.add(new Beam(beamType, 115, -Constants.BEAM_SPEED, beamSize, x - 10, y + 70));
				break;
			case JUMP_DOWN_LEFT:
				weapons.add(new Beam(beamType, 115, -Constants.BEAM_SPEED, beamSize, x - 10, y + 70));
				break;
			case CROUCH_DOWN_LEFT:
				weapons.add(new Beam(beamType, 115, -Constants.BEAM_SPEED, beamSize, x - 10, y + 70));
				break;
			case AIM_DOWN_RIGHT:
				weapons.add(new Beam(beamType, 235, Constants.BEAM_SPEED, beamSize, x + 70, y + 75));
				break;
			case WALK_DOWN_RIGHT:
				weapons.add(new Beam(beamType, 235, Constants.BEAM_SPEED, beamSize, x + 70, y + 75));
				break;
			case JUMP_DOWN_RIGHT:
				weapons.add(new Beam(beamType, 235, Constants.BEAM_SPEED, beamSize, x + 70, y + 75));
				break;
			case CROUCH_DOWN_RIGHT:
				weapons.add(new Beam(beamType, 235, Constants.BEAM_SPEED, beamSize, x + 70, y + 75));
				break;
		}
		
		weapons.get( weapons.size() - 1 ).updateInstance(instance);
		
		chargeLoop.stop();
		chargeStart.stop();
		
	}
	
	private void checkHurt() {
		if (!isHurt && instance.getEnemyManager().getAttackBoxes().size() > 0) {
			for (AttackBox box : instance.getEnemyManager().getAttackBoxes() ) {
				if ( box.intersects(hitBox.getBounds2D()) ) {
					hurtTimeout = 0;
					isHurt = true;
					isHurtStart = true;
					health -= box.getDamage();
					
					hurtX = box.getBounds2D().getX();
					hurtY = box.getBounds2D().getY();
					
				}
			}
		} else if (isHurt) {
			isHurtStart = false;
			if (hurtTimeout < 50)
				hurtTimeout++;
			else
				isHurt = false;
		}
	}
	
	public void charge() {
		charging = true;
	}
	
	public void resetCharge() {
		charging = false;
		beamSize = 5.0;
	}
	
	public void updateWeapons() {
		for (int i = 0; i < weapons.size(); i++) {
			weapons.get(i).updateInstance(instance);
			if (weapons.get(i).isRemoved()) {
				weapons.remove(weapons.get(i));
				removingWeapon = true;
			} else
				removingWeapon = false;
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
	
	public ArrayList<Weapon> getWeapons() {return oldWeapons;}
}