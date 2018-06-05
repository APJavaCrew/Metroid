package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;

import sprite.SpriteSheet;

public class Opening {
	
	private boolean isFinished = false;
	private int delay = (int) (Math.random() * 200), delayDelay = (int) (Math.random() * 20);
	private int shadowDelay = 0, shadowOpac = 320;
	private int rando = 200;
	private int x = 300, y = 300;
	
	private Font font, bigFont, smallFont;
	
	private Color startColor = Color.YELLOW, controlsColor = Color.WHITE, blank = new Color(0, 0, 0, 0);
	private int selectDelay = Constants.BLINK_TIME;
	private boolean isStartSelect = true;
	
	private boolean showControls = false;
	
	private SpriteSheet opening = new SpriteSheet("Metroid Intro.png", 576, 268);
	
	public Opening() {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("Serpentine-Bold.otf")).deriveFont((float) 150);
			bigFont = Font.createFont(Font.TRUETYPE_FONT, new File("Serpentine-Bold.otf")).deriveFont((float) 165);
			smallFont = Font.createFont(Font.TRUETYPE_FONT, new File("Serpentine-Bold.otf")).deriveFont((float) 45);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(opening.getSheet(), 0, 0, 1280, 720, null);
		if (delay > 0)
			delay --;
		else {
			rando = (int) (Math.random() * 70 + 70);
			if (delayDelay > 0)
				delayDelay --;
			else {
				delay = (int) (Math.random() * 200);
				delayDelay = (int) (Math.random() * 20);
				rando = 200;
			}
			
			shadowDelay = 20;
			shadowOpac = 320;
		}
		
		if (shadowDelay > 0) {
			if (shadowOpac > 255)
				g.setColor(new Color(100, 100, 100, 255));
			else
				g.setColor(new Color(120, 120, 120, shadowOpac));
			g.setFont(bigFont);
			g.drawString("Metroid", x - 30, y - 20);
			if (shadowOpac >= 10)
				shadowOpac -= 5;
		}
		
		g.setColor(new Color(0, 0, 0, rando));
		g.fillRect(0, 0, 1280, 720);
		
		g.setColor(new Color(170, 90, 200));
		g.setFont(font);
		g.drawString("Metroid", x, y);
		
		g.setFont(smallFont);
		if (selectDelay > 0 && isStartSelect) {
			selectDelay --;
			startColor = blank;
		} else if (selectDelay <= 0 && isStartSelect) {
			startColor = Color.WHITE;
			selectDelay --;
			if (selectDelay <= -Constants.BLINK_TIME)
				selectDelay = Constants.BLINK_TIME;
		}
		else
			startColor = Color.WHITE;
		
		if (selectDelay > 0 && !isStartSelect) {
			selectDelay --;
			controlsColor = blank;
		} else if (selectDelay <= 0 && !isStartSelect) {
			controlsColor = Color.WHITE;
			selectDelay --;
			if (selectDelay <= -Constants.BLINK_TIME)
				selectDelay = Constants.BLINK_TIME;
		}
		else
			controlsColor = Color.WHITE;
		
		g.setColor(startColor);
		g.drawString("Start", 580, 555);
		g.setColor(controlsColor);
		g.drawString("Controls", 540, 620);
		
		if (showControls) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1280, 720);
			
			g.setColor(Color.WHITE);
			g.drawString("Fire:", 400, 200);
			g.drawString("Move / Aim Up & Down:", 150, 300);
			g.drawString("Jump:", 380, 400);
			g.drawString("Aim Diagonally:", 240, 500);
			
			g.setColor(Color.CYAN);
			g.drawString("K", 1000, 200);
		}
		
	}
	
	public void select() {
		if (isStartSelect)
			isFinished = true;
		else
			showControls = !showControls;
	}
	
	public void changeSelection() {
		if (!showControls)
			isStartSelect = !isStartSelect;
		selectDelay = Constants.BLINK_TIME;
	}
	
	public boolean isFinished() {
		return isFinished;
	}
	
	public void restart() {
		isFinished = false;
	}

}
