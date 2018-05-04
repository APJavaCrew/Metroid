package camera;

public class Camera {
	
	private int x, y;
	private double dx = 0, dy = 0;
	private int height = 720, width = 1280;
	
	public Camera(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move(double dx, double dy) {
		x += dx;
		y += dy;
	}
	
}
