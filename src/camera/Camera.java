package camera;

import main.Runner;

public class Camera {
	
	Runner instance;
	private double x, y, dx, dy;
	
	public Camera(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getXOffset() {
		return x;
	}
	
	public double getYOffset() {
		return y;
	}
	
	public void updateInstance(Runner in) {
		instance = in;
	}
	
	public void move() {
		dx = -(instance.getPlayer().getX() - instance.getPlayer().getLastX());
		dy = -(instance.getPlayer().getY() - instance.getPlayer().getLastY());
		
		x += dx;
		y += dy;
	}
	
}
