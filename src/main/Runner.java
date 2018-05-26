package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;

import camera.Camera;
import enemy.EnemyManager;
import entity.TestEnemy;

import org.lwjgl.input.Controller;

import level.Room;
import player.Player;
import tiles.Tile;
import weapon.Beam;

public class Runner extends JFrame implements KeyListener {
	
	private Player player = new Player();
	private Camera camera = new Camera(0, 0);
	private EnemyManager enemyManager = new EnemyManager();
	static Room room;
	static Controller[] cont = new Controller[4];
	private boolean butt[][] = new boolean[4][10];
	private double[][] axis = new double[4][6];
	private double[][] pov = new double[4][2];
	private boolean multiplayer = true;
	
	private static boolean controlConnect = false;
	
	private boolean start = true, isRunning = true;
	private int fps = 60;
	private int windowWidth = 1280, windowHeight = 720;
	
	BufferedImage backBuffer;
	Insets insets;
	
	public Runner() {
		
		setSize(new Dimension(1280, 720));
		setPreferredSize(new Dimension(1280, 720));
		setFocusable(true);
		addKeyListener(this);
	}

	public static void main(String[] args) {
		Runner game = new Runner();
		game.run();
		System.exit(0);
	}
	
	private void init() {
		try {
			Controllers.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Controllers.poll();
		
		int contPos = 0;
		
		for (int i = 0; i < Controllers.getControllerCount(); i++) {
			if (Controllers.getController(i).getName().equals("MAYFLASH GameCube Controller Adapter")) {
				contPos = i;
				controlConnect = true;
				break;
			}
		}
		
		if (controlConnect) {
			for (int i = 0; i < 4; i++) {
				cont[i] = Controllers.getController(contPos + i);
			}
		} else {
			System.err.println("CameCube controllers not found!");
		}
		
		room = new Room("test", 0, 0);
		
		setTitle("Metroid");
		setSize(windowWidth, windowHeight);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		insets = getInsets();
		setSize(insets.left + windowWidth + insets.right, insets.top + windowHeight + insets.bottom);
		
		backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
		
	}

	private void run() {
		init();
		while (isRunning) {
			long time = System.currentTimeMillis();
			
			update();
			draw();
			
			time = (1000 / fps) - (System.currentTimeMillis() - time);
			System.out.println(time);
			if (time > 0) {
				try {
					Thread.sleep(time);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void draw() {
		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();
		
		bbg.setColor(new Color(255, 0, 255));
		bbg.fillRect(0, 0, windowWidth, windowHeight);

		room.draw(bbg);
		player.draw(bbg);
		enemyManager.draw(bbg);
		
		for (int i = 0; i < player.getBeams().size(); i++)
			player.getBeams().get(i).draw(bbg);
		
		g.drawImage(backBuffer, insets.left, insets.top, null);
		
	}
	
	private void update() {
		player.updateInstance(this);
		room.updateInstance(this);

		if (start)
			room.addEnemies();
		start = false;
		
		enemyManager.updateEnemies(this);
		
		if (controlConnect)
			pollControllers();
	}

	@SuppressWarnings("unused")
	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		if(key == KeyEvent.VK_A)
			axis[0][3] = -1;
		else if(key == KeyEvent.VK_D)
			axis[0][3] = 1;
		if(key == KeyEvent.VK_SPACE)
			player.jump();
		if (key == KeyEvent.VK_K)
			player.charge();
		if (key == KeyEvent.VK_W)
			axis[0][2] = -1;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		if(key == KeyEvent.VK_A)
			axis[0][3] = 0;
		else if(key == KeyEvent.VK_D)
			axis[0][3] = 0;
		if (key == KeyEvent.VK_K) {
			player.fire();
			player.resetCharge();
		}
		if (key == KeyEvent.VK_W)
			axis[0][2] = 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public Room getRoom() {
		return room;
	}
	
	public Area getRoomBounds() {
		Area total = new Area();
		
		for (ArrayList<Tile> y : room.getTiles()) {
			for (Tile x : y) {
				try {
					total.add(x.getHitBox());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return total;
	}
	
	public void pollControllers() {
		
		if (multiplayer) {
			for (int i = 0; i < 4; i++) {
				cont[i].poll();
				cont[0].setDeadZone(3, (float) 0.1); 
			}
			
			//X0, A1, B2, Y3, L4, R5, Z7, S9
			for(int x = 0; x < 4; x++) {
				for (int y = 0; y < 10; y++) {
					if (cont[x].isButtonPressed(y))
						butt[x][y] = true;
					else
						butt[x][y] = false;
				}
			}
			
			//CX0, CY1, Y2, X3, R4, L5
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 6; y++)
					axis[x][y] = cont[x].getAxisValue(y);
			}
			
			for (int x = 0; x < 4; x++) {
				pov[x][0] = cont[x].getPovY();
				pov[x][1] = cont[x].getPovX();
			}
		} else {
			cont[0].poll();
			
			for (int y = 0; y < 10; y++) {
				if (cont[0].isButtonPressed(y))
					butt[0][y] = true;
				else
					butt[0][y] = false;
			}
			
			for (int y = 0; y < 6; y++)
				axis[0][y] = cont[0].getAxisValue(y);
			
			pov[0][0] = cont[0].getPovY();
			pov[0][1] = cont[0].getPovX();
			
		}
	}

	public boolean[][] getButt() {
		return butt;
	}

	public double[][] getAxis() {
		return axis;
	}

	public double[][] getPov() {
		return pov;
	}
	
	public boolean[] getButt1() {
		return butt[0];
	}
	
	public double[] getAxis1() {
		return axis[0];
	}
	
	public double[] getPov1() {
		return pov[0];
	}
	
	public Player getPlayer() {
		return player;
	}

	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
}
