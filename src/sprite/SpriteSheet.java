package sprite;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class SpriteSheet {

	//Look at SimplePicture.java at loadOrFail()
	
	int w, h;
	BufferedImage sheet = null;
	
	public SpriteSheet(String path) {
		try {
			sheet = ImageIO.read(new File("sprites/" + path)); //TODO FIX THIS READING THING
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		w = sheet.getWidth();
		h = sheet.getHeight();
		
	}
	
	public SpriteSheet(String path, int individualWidth, int individualHeight) {
		try {
			sheet = ImageIO.read(new File("sprites/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.w = individualWidth;
		this.h = individualHeight;
	}
	
	public BufferedImage getSheet () {
		return sheet;
	}
	
	public BufferedImage getImageFile(String path) {
		
		BufferedImage newImage;
		
		try {
			newImage = ImageIO.read(new File("sprites/" + path));
		} catch (IOException e) {
			e.printStackTrace();
			newImage = new BufferedImage(10, 10, BufferedImage.TYPE_USHORT_GRAY);
		}
		
		return newImage;
		
		
	}
	
	public BufferedImage getSpriteAt(int xGrid, int yGrid) {
		if (sheet.equals(null))
			sheet = null;
		return sheet.getSubimage(xGrid * w, yGrid * h, w, h);
	}
	
	public void animate() {
		
	}
	
	//First digit of xIndexes is # of sprites
	//Separate indexes with '-'
	public BufferedImage[] getSpritesAt(String xIndexesString, String yIndexesString) {
		Scanner xIndexes = new Scanner(xIndexesString);
		Scanner yIndexes = new Scanner(yIndexesString);
		xIndexes.useDelimiter("-");
		yIndexes.useDelimiter("-");
		int length = xIndexes.nextInt();
		xIndexes.nextLine();
		int pos = 0;
		BufferedImage[] imgs = new BufferedImage[length];
		while (xIndexes.hasNextInt()) {
			try {
				imgs[pos] = getSpriteAt(xIndexes.nextInt(), yIndexes.nextInt());
			} catch(InputMismatchException e) {
				throw new RuntimeException("You didn't put the right values in for the indexes: " + e.getStackTrace());
			}
			pos++;
			xIndexes.nextLine();
			yIndexes.nextLine();
		}
		
		xIndexes.close();
		yIndexes.close();
		
		return imgs;
	}
	
}
