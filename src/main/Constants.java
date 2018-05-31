package main;

import sprite.Animation;
import sprite.SpriteSheet;

public class Constants {
	
	public static final boolean SHOWHITBOXES = true;
	
	//Player Physics
	
	//dx = horizontal speed, dy = vertical speed
	public static final double GRAVITY_ACCEL = 0.75; //dy increments by this amount when not on the ground
//	public final static double RUN_ACCEL = .25; //dx increments by this amount when speeding up
//	public final static double BRAKE_ACCEL = .1; //dx increments by this amount when slowing down
	public final static double JUMP_SPEED = -20; //dy is initially set to this value when jumping
	public final static double BONK_SPEED = 0.15; //dy is set to this when you bonk your head on a ceiling
//	public final static double BRAKE_DEADZONE = .05; //dx will be set to zero if its absolute value is less than this
//	public final static double MAX_RUN_SPEED = .5; //cannot run faster than this
	public final static double TERMINAL_VELOCITY = 20; //cannot fall faster than this
	public final static double BEAM_SPEED = 15; //speed at which fired beams travel (negative for left)
	public final static double MAX_BEAM_SIZE = 20; //max beam size
	public final static double KNOCKBACK_SPEED = .5; //speed at which Samus is knocked back when hit


	public static final int TILESIZE = 60;


	//Samus Animations
	public final static Animation samWalkLeft = new Animation(new SpriteSheet("SamWalkLeft.png", 32, 37).
			getSpritesAt("10-0-1-2-3-4-5-6-7-8-9-10", "0-0-0-0-0-0-0-0-0-0"), 5, 1, true);
	public final static Animation samWalkRight = new Animation(new SpriteSheet("SamWalkLeft.png", 32, 37).
			getSpritesAt("10-0-1-2-3-4-5-6-7-8-9-10", "0-0-0-0-0-0-0-0-0-0"), 5, 1, true);
	public final static Animation samStart = new Animation(new SpriteSheet("SamStart.png", 24, 40).getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);
	public final static Animation samFireUpL = new Animation(new SpriteSheet("SamFireUpLeft.png", 22, 44).getSpritesAt("3-0-1-2", "0-0-0"), 10, 1, true);
	public final static Animation samFireUpR = new Animation(new SpriteSheet("SamFireUpRight.png", 22, 44).getSpritesAt("3-0-1-2", "0-0-0"), 10, 1, true);

	
	//Enemy Animations
	public static final Animation geemerWalk = new Animation(new SpriteSheet("Geemer.png", 23, 15).getSpritesAt("3-0-1-2", "0-0-0"), 1, 1, true);
	public static final Animation geemerDie = new Animation(new SpriteSheet("GeemerDie.png", 23, 24).getSpritesAt("3-0-1-2", "0-0-0"), 20, 1, false);
	public static final Animation geemerLegz = new Animation(new SpriteSheet("GeemerLegz.png", 23, 17).getSpritesAt("2-0-1", "0-0"), 30, 1, true);
	
	public static final Animation ripperL = new Animation(new SpriteSheet("Ripper.png", 21, 12).getSpritesAt("3-0-1-2", "0-0-0"), 70, 1, true);
	public static final Animation ripperR = new Animation(new SpriteSheet("Ripper.png", 21, 12).getSpritesAt("3-7-8-9", "0-0-0"), 70, 1, true);
	public static final Animation ripperTurnR = new Animation(new SpriteSheet("Ripper.png", 21, 12).getSpritesAt("4-3-4-5-6", "0-0-0-0"), 7, 1, false);
	public static final Animation ripperTurnL = new Animation(new SpriteSheet("Ripper.png", 21, 12).getSpritesAt("4-6-5-4-3", "0-0-0-0"), 7, 1, false);
	
	public static final Animation shriekBat = new Animation( new SpriteSheet("ShriekBatCronch.png", 28, 24).getSpritesAt("2-0-1", "0-0"), 30, 1, true );



}
