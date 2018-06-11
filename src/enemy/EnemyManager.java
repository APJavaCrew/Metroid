package enemy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import main.Runner;

public class EnemyManager {
	
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	Area freezeBox = new Area( new Rectangle(0, 0, 0, 0));
	
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
						break;
					case 3:
						add( new ShriekBat(fileRead.nextInt(), fileRead.nextInt()) );
						break;
					case 4:
						add( new Dragon(fileRead.nextInt(), fileRead.nextInt()));
						break;
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
		freezeBox.subtract(freezeBox);
		
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).updateInstance(in);
			
			if (enemies.get(i).isFrozen)
				freezeBox.add(enemies.get(i).getHitBox());
			
			if (!enemies.get(i).isAlive()) {
				System.out.println("death ;)");
				enemies.remove(i);
			}
			
		}
		
	}
	
	public Area getFreezeBox() {
		return freezeBox;
	}
	
	public void draw(Graphics2D g) {
		for (Enemy e : enemies) {
			g = instance.getBackBuffer().createGraphics();
			g.translate( (int) instance.getCamera().getXOffset(), (int) instance.getCamera().getYOffset());
			e.draw(g);
		}
	}
	
	public ArrayList<AttackBox> getAttackBoxes() {
		ArrayList<AttackBox> boxes = new ArrayList<AttackBox>();
		for (Enemy e : enemies) {
			boxes.add(e.getAttackBox());
		}
		return boxes;
	}

}
