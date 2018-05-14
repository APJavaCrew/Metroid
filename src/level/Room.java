package level;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import entity.Tile;
import tiles.MetalTile;
import tiles.TestTile;

/**
 * 
 * @author Nolan Manor
 *
 */
public class Room {
	
	private double x, y;
	private int[][] tileTypes;
	private ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
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
	
	public ArrayList<ArrayList<Tile>> getTiles() {
		return tiles;
	}
	
	public void draw(Graphics g) {
		for (ArrayList<Tile> a : tiles) {
			for (Tile b : a) {
				b.draw(g);
			}
		}
	}
	
	public void load() {
		tileTypes = new int[fileRead.nextInt()][fileRead.nextInt()];
		for (int y = 0; y < tileTypes.length; y++) {
			for (int x = 0; x < tileTypes[0].length; x++) {
				tileTypes[y][x] = fileRead.nextInt();
			}
		}
		for (int y = 0; y < tileTypes.length; y++) {
			tiles.add(new ArrayList<Tile>());
			for (int x = 0; x < tileTypes[0].length; x++) {
				switch (tileTypes[y][x]) {
					case 99:
						tiles.get(y).add(new TestTile(y, x, x * 81, y * 85));
						break;
					case 0:
						continue;
					case 1:
						tiles.get(y).add(new MetalTile(y, x, x * 81, y * 85));
						break;
				}
			}
		}
	}

}
