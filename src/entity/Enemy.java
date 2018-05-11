package entity;

import java.awt.geom.Area;

public class Enemy extends Being {
	protected Area attackbox;
	private double x, y; //position
	private double dx, dy; //horiz/vert speed

	@Override
	public void move() {
		y=0;
		x=0;
		dx=0;
		dy=0;
		
	}

}
