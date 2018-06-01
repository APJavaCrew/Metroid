package enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import main.Constants;
import main.Runner;

public class Dragon extends Enemy {
	
	AffineTransform at;
	
	private int size = 3;
	private int shootWait;
	
	public Dragon(double x, double y) {
		super(x, y);
		
		animation = Constants.dragon;
		
		at = new AffineTransform();
		at.translate(x, y);
		
		hitBox = new Area(new Rectangle(0, 0, w, h));
		
	}
	
	public void draw(Graphics2D g) {
		
	}
	
}
