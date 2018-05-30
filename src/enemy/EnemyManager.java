package enemy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import main.Runner;

public class EnemyManager {
	
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	Runner instance;
	
	public EnemyManager(Runner in) {
		instance = in;
	}
	
	public void addFromFile(String path) {
		Scanner fileRead;
		File file = new File("levels/" + path);
		try {
			fileRead = new Scanner(file);
			int size = fileRead.nextInt();
			for (int i = 0; i < size; i++) {
				switch (fileRead.nextInt()) {
					case 1:
						add( new Geemer(fileRead.nextInt(), fileRead.nextInt(), fileRead.nextInt(), instance ) );
						break;
					case 2:
						add( new Ripper(fileRead.nextInt(), fileRead.nextInt(), instance) );
					case 3:
						add( new ShriekBat(fileRead.nextInt(), fileRead.nextInt()) );
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void add(Enemy enemy) {
		enemies.add(enemy);
	}
	
	public void updateEnemies(Runner in) {
		instance = in;
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).updateInstance(in);
			
			if (!enemies.get(i).isAlive())
				enemies.remove(i);
			
		}
		
	}
	
	public void draw(Graphics g) {
		for (Enemy e : enemies) {
			g = instance.getBackBuffer().getGraphics();
			g.translate( (int) instance.getCamera().getXOffset(), (int) instance.getCamera().getYOffset());
			e.draw(g);
		}
	}

}
