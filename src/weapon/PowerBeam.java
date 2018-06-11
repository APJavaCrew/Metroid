/*package weapon;

import java.awt.Color;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PowerBeam extends Beam {
	
	public PowerBeam(double deg, double velModifier, double size, double x, double y) {
		super(deg, velModifier, size, x, y);
		setColor(new Color(255, 200, 0));
		type = "power";
		
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File ("Music/shoot.wav"));
			Clip shoop = AudioSystem.getClip();
			shoop.open(stream);
			shoop.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
*/