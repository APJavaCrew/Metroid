package level;

import java.awt.Graphics;
import java.awt.geom.Area;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import entity.Entity;
import main.Constants;
import main.Runner;
import sprite.SpriteSheet;
import tiles.MetalTile;
import tiles.TestTile;
import tiles.Tile;

/**
 * 
 * @author Nolan Manor
 *
 */
public class Room extends Entity {
	
	private int[][] tileTypes;
	private ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
	Scanner fileRead;
	Runner instance;
	
	SpriteSheet backgroundImage;
	ArrayList<SpriteSheet> backgroundArray = new ArrayList<SpriteSheet>();
	
	public Room(String path, double x, double y) {
		
		File file;
		file = new File("levels/" + path);
		try {
			fileRead = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		load();
		
	}
	
	public ArrayList<ArrayList<Tile>> getTiles() {
		return tiles;
	}
	
	public void draw(Graphics g) {
		g.drawImage(backgroundImage.getSheet(), (int) x, (int) y, w, h, null);
		for (ArrayList<Tile> a : tiles) {
			for (Tile b : a) {
				b.draw(g);
			}
		}
	}
	
	private void translateBackground() {
		
	}
	
	public void load() {
		
		switch(fileRead.nextInt()) {
			default:
				break;
			case 1:
				backgroundImage = new SpriteSheet("LavaBack.png");
		}
		
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
						tiles.get(y).add(new TestTile(this.x + x * Constants.TILESIZE, this.y + y * Constants.TILESIZE));
						break;
					case 0:
						continue;
					case 1:
						tiles.get(y).add(new MetalTile(this.x + x * Constants.TILESIZE, this.y + y * Constants.TILESIZE));
						break;
				}
				if (y == 1)
					h += Constants.TILESIZE;
			}
			w += Constants.TILESIZE;
		}
		
		w = tiles.size() * Constants.TILESIZE;
		h = tiles.get(0).size() * Constants.TILESIZE;
		
		w = 1280;
		h = 1000;
	}
	
	public void addEnemies() {
		instance.getEnemyManager().addFromFile("testEnemies");
	}
	
	public void updateInstance(Runner in) {
		instance = in;
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
	
	public ArrayList<Tile> getIntersectingTiles(Area box) {
		ArrayList<Tile> total = new ArrayList<Tile>();
		for (ArrayList<Tile> e : tiles) {
			for (Tile j : e) {
				if (box.intersects(j.getHitBox().getBounds2D()))
					total.add(j);
			}
		}
		return total;
	}

}
