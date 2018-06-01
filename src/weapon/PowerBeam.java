package weapon;

import java.awt.Color;

public class PowerBeam extends Beam {

	public PowerBeam(double deg, double velModifier, double size, double x, double y) {
		super(deg, velModifier, size, x, y);
		setColor(new Color(255, 200, 0));
		type = "power";
	}

}
