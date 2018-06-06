package main;

import sprite.Animation;
import sprite.SpriteSheet;

public class Constants {
	
	public static final boolean SHOWHITBOXES = false;
	
	//Player Physics
	
	//dx = horizontal speed, dy = vertical speed
	public static final double GRAVITY_ACCEL = 0.75; //dy increments by this amount when not on the ground
//	public final static double RUN_ACCEL = .25; //dx increments by this amount when speeding up
//	public final static double BRAKE_ACCEL = .1; //dx increments by this amount when slowing down
	public final static double JUMP_SPEED = -20; //dy is initially set to this value when jumping
	public final static double BONK_SPEED = 2; //dy is set to this when you bonk your head on a ceiling
//	public final static double BRAKE_DEADZONE = .05; //dx will be set to zero if its absolute value is less than this
//	public final static double MAX_RUN_SPEED = .5; //cannot run faster than this
	public final static double TERMINAL_VELOCITY = 20; //cannot fall faster than this
	public final static double BEAM_SPEED = 15; //speed at which fired beams travel (negative for left)
	public final static double MAX_BEAM_SIZE = 20; //max beam size
	public final static double KNOCKBACK_SPEED = .5; //speed at which Samus is knocked back when hit


	public static final int TILESIZE = 60;


	//Samus Animations

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
	public final static Animation samStart = new Animation(new SpriteSheet("SamStart.png", 24, 40).
			getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);

	public final static Animation samMorph = new Animation(new SpriteSheet("SamMorph.png", 16, 16).
			getSpritesAt("8-0-1-2-3-4-5-6-7", "0-0-0-0-0-0-0-0"), 5, 1, true);
	public final static Animation samJumpSpinLeft = new Animation(new SpriteSheet("SamJumpSpinLeft.png", 27, 27).
			getSpritesAt("8-0-1-2-3-4-5-6-7", "0-0-0-0-0-0-0-0"), 5, 1, true);
	public final static Animation samJumpSpinRight = new Animation(new SpriteSheet("SamJumpSpinRight.png", 27, 27).
			getSpritesAt("8-0-1-2-3-4-5-6-7", "0-0-0-0-0-0-0-0"), 5, 1, true);
	public final static Animation samAimLeft = new Animation(new SpriteSheet("SamAimLeft.png", 26, 36).
			getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);
	public final static Animation samWalkLeft = new Animation(new SpriteSheet("SamWalkLeft.png", 36, 37).
			getSpritesAt("10-0-1-2-3-4-5-6-7-8-9", "0-0-0-0-0-0-0-0-0-0"), 5, 1, true);
//	public final static Animation samJumpLeft = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
	public final static Animation samCrouchLeft = new Animation(new SpriteSheet("SamCrouchLeft.png", 24, 25).
			getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);
	public final static Animation samAimRight = new Animation(new SpriteSheet("SamAimRight.png", 26, 36).
			getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);
	public final static Animation samWalkRight = new Animation(new SpriteSheet("SamWalkRight.png", 36, 37).
			getSpritesAt("10-0-1-2-3-4-5-6-7-8-9-10", "0-0-0-0-0-0-0-0-0-0"), 5, 1, true);
//	public final static Animation samJumpRight = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
	public final static Animation samCrouchRight = new Animation(new SpriteSheet("SamCrouchRight.png", 24, 25).
			getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);
	public final static Animation samFireUpL = new Animation(new SpriteSheet("SamAimUpL.png", 22, 44).
			getSpritesAt("3-0-1-2", "0-0-0"), 10, 1, true);
//	public final static Animation samJumpUpL = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
	public final static Animation samFireUpR = new Animation(new SpriteSheet("SamAimUpR.png", 22, 44).
			getSpritesAt("3-0-1-2", "0-0-0"), 10, 1, true);
//	public final static Animation samJumpUpR = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
//	public final static Animation samJumpDownL = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
//	public final static Animation samJumpDownR = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
	public final static Animation samAimUpLeft = new Animation(new SpriteSheet("SamAimUpLeft.png", 25, 40).
			getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);
//	public final static Animation samWalkUpRight = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
//	public final static Animation samJumpUpRight = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
	public final static Animation samCrouchUpLeft = new Animation(new SpriteSheet("SamusCrouchUpLeft.png", 23, 30).
			getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);
	public final static Animation samAimUpRight = new Animation(new SpriteSheet("SamAimUpRight.png", 25, 40).
			getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);
//	public final static Animation samWalkUpRight = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
//	public final static Animation samJumpUpRight = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
	public final static Animation samCrouchUpRight = new Animation(new SpriteSheet("SamusCrouchUpRight.png", 23, 30).
			getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);
	public final static Animation samAimDownLeft = new Animation(new SpriteSheet("SamAimDownLeft.png", 24, 36).
			getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);
//	public final static Animation samWalkDownLeft = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
//	public final static Animation samJumpDownLeft = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
	public final static Animation samCrouchDownLeft = new Animation(new SpriteSheet("SamusCrouchDownLeft.png", 23, 25).
			getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);
	public final static Animation samAimDownRight = new Animation(new SpriteSheet("SamAimDownRight.png", 24, 36).
			getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);
//	public final static Animation samWalkDownRight = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
//	public final static Animation samJumpDownRight = new Animation(new SpriteSheet("SamX.png", x, y).
//			getSpritesAt("", ""), 5, 1, true);
	public final static Animation samCrouchDownRight = new Animation(new SpriteSheet("SamusCrouchDownRight.png", 23, 25).
			getSpritesAt("3-0-1-2", "0-0-0"), 5, 1, true);
	
	
	//Enemy Animations
	public static final Animation geemerWalk = new Animation(new SpriteSheet("Geemer.png", 23, 15).getSpritesAt("3-0-1-2", "0-0-0"), 1, 1, true);
	public static final Animation geemerDie = new Animation(new SpriteSheet("GeemerDie.png", 23, 24).getSpritesAt("3-0-1-2", "0-0-0"), 20, 1, false);
	public static final Animation geemerLegz = new Animation(new SpriteSheet("GeemerLegz.png", 23, 17).getSpritesAt("2-0-1", "0-0"), 30, 1, true);
	
	public static final Animation ripperL = new Animation(new SpriteSheet("Ripper.png", 21, 12).getSpritesAt("3-0-1-2", "0-0-0"), 70, 1, true);
	public static final Animation ripperR = new Animation(new SpriteSheet("Ripper.png", 21, 12).getSpritesAt("3-7-8-9", "0-0-0"), 70, 1, true);
	public static final Animation ripperTurnR = new Animation(new SpriteSheet("Ripper.png", 21, 12).getSpritesAt("4-3-4-5-6", "0-0-0-0"), 7, 1, false);
	public static final Animation ripperTurnL = new Animation(new SpriteSheet("Ripper.png", 21, 12).getSpritesAt("4-6-5-4-3", "0-0-0-0"), 7, 1, false);
	
	public static final Animation shriekBat = new Animation( new SpriteSheet("ShriekBatCronch.png", 28, 24).getSpritesAt("2-0-1", "0-0"), 30, 1, true );

	public static final Animation dragon = new Animation( new SpriteSheet("DragonEnemy.png", 29, 32)
			.getSpritesAt("4-1-2-3-0", "0-0-0-0"), 30, 1, false );
	
	
	//Misc
	public static final int BLINK_TIME = 35;


}
