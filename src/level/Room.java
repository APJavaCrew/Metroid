package level;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import entity.Tile;

/**
 * 
 * @author Nolan Manor
 *
 */
public class Room {
	
	private double x, y;
	private int[][] tileTypes;
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private File file;
	Scanner fileRead;
	
	public Room(String path) {
		file = new File("levels/" + path);
		try {
			fileRead = new Scanner(file);
			fileRead.useDelimiter("\\s*");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Error reading room file");
		}
	}
	
	public void draw(Graphics g) {
		
	}
	
	public void load() {
		for (int y = 0; y < tileTypes.length; y++) {
			for (int x = 0; x < tileTypes[0].length; x++) {
				
			}
		}
	}

}
