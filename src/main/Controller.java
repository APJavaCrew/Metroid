package main;
import net.java.games.input.*;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
public class Controller {
	int NUM_BUTTONS=8;
	// globals
	private Controller controller; // for the game pad
	public Controller()
	{
		// get the controllers
		ControllerEnvironment ce =
		ControllerEnvironment.getDefaultEnvironment();
		Controller[] cs = (Controller[]) ce.getControllers();
		if (cs.length == 0) {
			System.out.println("No controllers found");
			System.exit(0);
		}
		else{
			System.out.println("Num. controllers: " + cs.length);}
		// get the game pad controller
		controller = findGamePad(cs);
		// collect indices for the required game pad components
		findCompIndices(controller);
		} // end of GamePadController()
	}
	private Controller findGamePad(Controller[] cs)
	{
		Controller.Type type;
		int index = 0;
		while(index < cs.length) {
			type = cs[index].getType();
			if ((type == Controller.Type.GAMEPAD) ||
					(type == Controller.Type.STICK))
					break;
				index++;
				}
		if (index == cs.length) {
			System.out.println("No game pad found");
			System.exit(0);
		}
		else
			System.out.println("Game pad index: " + i);
		return cs[index];
	} // end of findGamePad()
	// globals
	private Component[] comps; // holds the components
	// comps[] indices for specific components
	private int xAxisIdx, yAxisIdx, zAxisIdx, rzAxisIdx;
	// indices for the analog sticks axes
	private int povIdx; // index for the POV hat
	
	private void findCompIndices(Controller controller)
	{
		comps = controller.getComponents();
		if (comps.length == 0) {
			System.out.println("No Components found");
			System.exit(0);
		}
		else
			System.out.println("Num. Components: " + comps.length);
		// get indices for sticks axes: (x,y) and (z,rz)
		xAxisIdx = findCompIndex(comps,
				Component.Identifier.Axis.X, "x-axis");
		yAxisIdx = findCompIndex(comps,
				Component.Identifier.Axis.Y, "y-axis");
		zAxisIdx = findCompIndex(comps,
			Component.Identifier.Axis.Z, "z-axis");
		rzAxisIdx = findCompIndex(comps,
			Component.Identifier.Axis.RZ, "rz-axis");
	// get POV hat index
		povIdx = findCompIndex(comps,
	Component.Identifier.Axis.POV, "POV hat");
	findButtons(comps);
	} // end of findCompIndices()
	private int findCompIndex(Component[] comps,
	Component.Identifier id, String nm)
		{
			Component c;
			for(int i=0; i < comps.length; i++) {
				c = comps[i];
				if ((c.getIdentifier() == id) && !c.isRelative()) {
					System.out.println("Found " + c.getName() + "; index: " + i);
					return i;
				}
			}
			System.out.println("No " + nm + " component found");
			return -1;
			} // end of findCompIndex()
	private void findButtons(Component[] comps)
	{
		
		buttonsIdx = new int[NUM_BUTTONS];
		int numButtons = 0;
		Component c;
		for(int i=0; i < comps.length; i++) {
			c = comps[i];
			if (isButton(c)) { // deal with a button
				if (numButtons == NUM_BUTTONS) // already enough buttons
					System.out.println("Found an extra button; index: " +i + ". Ignoring it");
			else {
				buttonsIdx[numButtons] = i; // store button index
				System.out.println("Found " + c.getName() + "; index: " + i);
				numButtons++;
			}
			}
		}
	// fill empty spots in buttonsIdx[] with -1's
	if (numButtons < NUM_BUTTONS) {
	System.out.println("Too few buttons (" + numButtons +"); expecting " + NUM_BUTTONS);
	while (numButtons < NUM_BUTTONS) {
	buttonsIdx[numButtons] = -1;
	numButtons++;
	}
	}
	} // end of findButtons()
	private boolean isButton(Component c)
	{
		if (!c.isAnalog() && !c.isRelative()) { // digital & absolute
			String className = c.getIdentifier().getClass().getName();
			if (className.endsWith("Button"))
				return true;
			}
		return false;
	} // end of isButton()
	public int getXYStickDir()
	// return the (x,y) analog stick compass direction
	{
	if ((xAxisIdx == -1) || (yAxisIdx == -1)) {
	System.out.println("(x,y) axis data unavailable");
	return NONE;
	}
	else
	return getCompassDir(xAxisIdx, yAxisIdx);
	} // end of getXYStickDir()
	public int getZRZStickDir()
	// return the (z,rz) analog stick compass direction
	{
	if ((zAxisIdx == -1) || (rzAxisIdx == -1)) {
	System.out.println("(z,rz) axis data unavailable");
	return NONE;
	}
	else
	return getCompassDir(zAxisIdx, rzAxisIdx);
	} // end of getXYStickDir()
	// global public stick and hat compass positions
	public static final int NUM_COMPASS_DIRS = 9;
	public static final int NW = 0;
	public static final int NORTH = 1;
	public static final int NE = 2;
	public static final int WEST = 3;
	public static final int NONE = 4; // default value
	public static final int EAST = 5;
	public static final int SW = 6;
	public static final int SOUTH = 7;
	public static final int SE = 8;
	private int getCompassDir(int xA, int yA)
	{
	float xCoord = comps[xA].getPollData();
	float yCoord = comps[yA].getPollData();
	int xc = Math.round(xCoord);
	int yc = Math.round(yCoord);
	if ((yc == -1) && (xc == -1)) // (y,x)
	return NW;
	else if ((yc == -1) && (xc == 0))
	return NORTH;
	else if ((yc == -1) && (xc == 1))
	return NE;
	else if ((yc == 0) && (xc == -1))
	return WEST;
	else if ((yc == 0) && (xc == 0))
	return NONE;
	else if ((yc == 0) && (xc == 1))
	return EAST;
	else if ((yc == 1) && (xc == -1))
	return SW;
	else if ((yc == 1) && (xc == 0))
	return SOUTH;
	else if ((yc == 1) && (xc == 1))
	//Java Prog. Techniques for Games. Chapter 28.9. Game Pad Draft #3 (9th Oct. 06)
	//23 © Andrew Davison 2006
	return SE;
	else {
	System.out.println("Unknown (x,y): (" + xc + "," + yc + ")");
	return NONE;
	}
	} // end of getCompassDir()
	//The axes value are retrieved by using their index positions and
	//Component.getPollData():
	float xCoord = comps[xA].getPollData();
	float yCoord = comps[yA].getPollData();
	//They're rounded to integers (either 0 or 1), and a series of if-tests determine which
	//compass setting should be returned. The rounding shows that position information is
	//lost, but with the aim of simplifying the interface.
	//The compass constants in GamePadController are public so they can be utilized in
	//other classes which use the compass data.
	//5.6. Reading the POV Hat
	//The polling of the hat returns a float corresponding to different combinations of key
	//presses (see Figure 9). getHatDir() maps this to the same compass directions as used
	//by the sticks.
	public int getHatDir()
	{
	if (povIdx == -1) {
	System.out.println("POV hat data unavailable");
	return NONE;
	}
	else {
	float povDir = comps[povIdx].getPollData();
	if (povDir == POV.CENTER) // 0.0f
	return NONE;
	else if (povDir == POV.DOWN) // 0.75f
	return SOUTH;
	else if (povDir == POV.DOWN_LEFT) // 0.875f
	return SW;
	else if (povDir == POV.DOWN_RIGHT) // 0.625f
	return SE;
	else if (povDir == POV.LEFT) // 1.0f
	return WEST;
	else if (povDir == POV.RIGHT) // 0.5f
	return EAST;
	else if (povDir == POV.UP) // 0.25f
	return NORTH;
	else if (povDir == POV.UP_LEFT) // 0.125f
	return NW;
	else if (povDir == POV.UP_RIGHT) // 0.375f
	return NE;
	else { // assume center
	System.out.println("POV hat value out of range: " + povDir);
	return NONE;
	}
	}
	//Java Prog. Techniques for Games. Chapter 28.9. Game Pad Draft #3 (9th Oct. 06)
	//24 © Andrew Davison 2006
	} // end of getHatDir()
	public boolean[] getButtons()
	{
	boolean[] buttons = new boolean[NUM_BUTTONS];
	float value;
	for(int i=0; i < NUM_BUTTONS; i++) {
	value = comps[ buttonsIdx[i] ].getPollData();
	buttons[i] = ((value == 0.0f) ? false : true);
	}
	return buttons;
	} // end of getButtons()
/*

	Controller[] controllers=(Controller[]) ControllerEnvironment.getDefaultEnvironment().getControllers();
	Controller firstMouse=null;
	for(int i=0; i<contollers.length&&firstMouse==null;i++)
	{
		
	}*/
	
	
	


