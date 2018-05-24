package main;

import sprite.Animation;
import sprite.SpriteSheet;

public class Constants {
	
	public static final boolean SHOWHITBOXES = false;
	
	//Physics Things
	
	//dx = horizontal speed, dy = vertical speed
	public static final double GRAVITY_ACCEL = 0.075; //dy increments by this amount when not on the ground
	public final static double RUN_ACCEL = .25; //dx increments by this amount when speeding up
	public final static double BRAKE_ACCEL = .1; //dx increments by this amount when slowing down
	
	public final static double BRAKE_DEADZONE = .05; //dx will be set to zero if its absolute value is less than this
	
	public final static double MAX_RUN_SPEED = .5; //cannot run faster than this
	public final static double TERMINAL_VELOCITY = 1; //cannot fall faster than this
	
	//Samus Animations
	public final static Animation samWalkLeft = new Animation(new SpriteSheet("SamWalkLeft.png", 32, 37).
			getSpritesAt("10-0-1-2-3-4-5-6-7-8-9-10", "0-0-0-0-0-0-0-0-0-0"), 30, 1, true);
	public final static Animation samWalkRight = new Animation(new SpriteSheet("SamWalkRight.png", 32, 37).
			getSpritesAt("10-0-1-2-3-4-5-6-7-8-9-10", "0-0-0-0-0-0-0-0-0-0"), 30, 1, true);
	public final static Animation samStart = new Animation(new SpriteSheet("SamStart.png", 24, 40).getSpritesAt("3-0-1-2", "0-0-0"), 50, 1, true);

	
	//Enemy Animations
	public static final Animation geemerUR = new Animation(new SpriteSheet("Geemer.png", 23, 15).getSpritesAt("3-0-1-2", "0-0-0"), 1, 1, true);
	public static final Animation geemerTurn = new Animation(new SpriteSheet("GeemerTurn.png", 23, 24).getSpritesAt("3-0-1-2", "0-0-0"), 20, 1, false);
	

}
