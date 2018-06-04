package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;

import sprite.SpriteSheet;

public class Opening {
	
	private boolean isFinished = false;
	int delay = (int) (Math.random() * 300), delayDelay = (int) (Math.random() * 20);
	int rando = 200;
	
	Font font;
	
	SpriteSheet opening = new SpriteSheet("Metroid Intro.png", 576, 268);
	
	public Opening() {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("Serpentine-Bold.otf")).deriveFont((float) 150);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(opening.getSheet(), 0, 0, 1280, 720, null);
		if (delay > 0)
			delay --;
		else {
			rando = (int) (Math.random() * 70 + 100);
			if (delayDelay > 0)
				delayDelay --;
			else {
				delay = (int) (Math.random() * 300);
				delayDelay = (int) (Math.random() * 20);
				rando = 200;
			}
		}
		g.setColor(new Color(0, 0, 0, rando));
		g.fillRect(0, 0, 1280, 720);
		
		g.setColor(new Color(100, 80, 100));
		g.setFont(font);
		g.drawString("Metroid", 500, 500);
		
	}
	
	public boolean isFinished() {
		return isFinished;
	}

}
