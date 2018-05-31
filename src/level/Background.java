package level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Runner;

public class Background {

	Runner instance;
	BufferedImage sprite;
	private int x, y, w, h;
	private double dx, dy;
	
	public Background(int x, int y, int w, int h, Runner in) {
		this.x = x; this.y = y; this.w = w; this.h= h; this.instance = in;
	}
	
	public void draw(Graphics g) {
		
		dx = -instance.getCamera().getDX() / 2;
		
		g.drawImage(sprite, x, y, null);
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	
}
