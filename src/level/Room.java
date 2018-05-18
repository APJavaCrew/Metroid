package level;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import entity.Entity;
import main.Runner;
import tiles.MetalTile;
import tiles.TestTile;
import tiles.Tile;

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
	Runner instance;
	
	public Room(String path) {
		file = new File("levels/" + path);
		try {
			fileRead = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
						tiles.get(y).add(new TestTile(x * 81, y * 85));
						break;
					case 0:
						continue;
					case 1:
						tiles.get(y).add(new MetalTile(x * 81, y * 85));
						break;
				}
			}
		}
	}
	
	public void updateInstance(Runner i) {
		instance = i;
	}
	
	public ArrayList<Tile> getIntersectingTiles(Entity ent) {
		ArrayList<Tile> total = new ArrayList<Tile>();
		for (ArrayList<Tile> e : tiles) {
			for (Tile j : e) {
				if (j.getHitBox().intersects(ent.getHitBox().getBounds2D()))
					total.add(j);
			}
		}
		return total;
	}

}
