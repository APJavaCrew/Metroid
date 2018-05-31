package camera;

import main.Runner;

public class Camera {
	
	Runner instance;
	private double x, y, dx, dy, targetX, targetY;
	
	public Camera(double x, double y) {
		this.x = x;
		this.y = y;
		targetX = x;
		targetY = y;
	}
	
	public double getXOffset() {
		return x;
	}
	
	public double getYOffset() {
		return y;
	}
	
	public double getDX() {
		return dx;
	}
	
	public double getDY() {
		return dy;
	}
	
	public void updateInstance(Runner in) {
		instance = in;
	}
	
	public void move() {
		dx = -(instance.getPlayer().getX() - instance.getPlayer().getLastX());
		dy = -(instance.getPlayer().getY() - instance.getPlayer().getLastY());
		
		targetX += dx;
		targetY += dy;
		
		x += (targetX - x) * 0.125;
		y += (targetY - y) * 0.125;
	}
	
}
