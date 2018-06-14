package enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;

import main.Constants;
import main.Runner;

public class Dragon extends Enemy {
	
	AffineTransform at;
	
	private int size = 3;
	private int shootWait = 120;
	
	private Area viewBox;
	
	ArrayList<DragonShot> shots = new ArrayList<DragonShot>();
	
	public Dragon(double x, double y) {
		super(x, y);
		
		animation = Constants.dragon;
		
		health = 9;
		
		at = new AffineTransform();
		at.translate(x, y);
		
		hitBox = new Area(new Rectangle(0, 0, w, h));
		attackBox = new AttackBox(hitBox, 10);
		
	}
	
	public void draw(Graphics2D g) {
		
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.scale(size, size);
		g.transform(at);
		g.drawImage(animation.getSprite(), 0, 0, null);
		animation.update();

		drawShots(g);
		
		if (Constants.SHOWHITBOXES) {
			at = new AffineTransform();
			at.translate(0, 0);
			g.transform(at);
			g.setColor(Color.WHITE);
			g.fill(hitBox);
		}
		
		move();
		
	}
	
	public void move() {
		updateHitBoxes();
		
		attack();
		
		checkBeingHurt();
		
	}
	
	
	protected void updateHitBoxes() {
		w = animation.getSprite().getWidth();
		h = animation.getSprite().getHeight();
		
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.scale(size, size);
		Rectangle boxRect = new Rectangle(0, 0, w, h);
		hitBox = new Area(boxRect);
		hitBox.transform(at);
		attackBox.set(hitBox);
	}
	
	private void drawShots(Graphics2D g) {
		for (DragonShot s : shots)
			s.draw(g);
	}
	
	private void attack() {
		if (shootWait == 0) {
			instance.getEnemyManager().add(new DragonShot(x, y, -1));
			shootWait = 120;
		} else {
			shootWait--;
		}
		
		if (shootWait == 7)
			animation.restart();
	}
	
}
