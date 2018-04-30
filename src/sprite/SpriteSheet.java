package sprite;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class SpriteSheet {

	//Look at SimplePicture.java at loadOrFail()
	
	int w, h;
	BufferedImage sheet = null;
	
	public SpriteSheet(String path) {
		try {
			sheet = ImageIO.read(new File("res/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		w = sheet.getWidth();
		h = sheet.getHeight();
		
	}
	
	public SpriteSheet(String path, int individualWidth, int individualHeight) {
		try {
			sheet = ImageIO.read(new File("res/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.w = individualWidth;
		this.h = individualHeight;
	}
	
	public BufferedImage getSheet () {
		return sheet;
	}
	
	public BufferedImage getImageAtLocation(String path) {
		
		BufferedImage newImage;
		
		try {
			newImage = ImageIO.read(new File("res/" + path));
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
	
	//First digit is # of sprites
	//Separate indexes with '-'
	public BufferedImage[] getSpritesAt(Scanner xIndexes, Scanner yIndexes) {
		xIndexes.useDelimiter("-");
		int length = xIndexes.nextInt();
		int pos = 0;
		BufferedImage[] imgs = new BufferedImage[length];
		while (xIndexes.hasNextInt()) {
			imgs[pos] = getSpriteAt(xIndexes.nextInt(), yIndexes.nextInt());
			pos++;
			xIndexes.nextLine();
			yIndexes.nextLine();
		}
		return imgs;
	}
	
}
