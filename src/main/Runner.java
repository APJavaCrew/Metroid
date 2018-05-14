package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Controller;

import entity.Player;
import level.Room;

public class Runner extends JPanel implements KeyListener {
	
	private Player player = new Player();
	static Room test;
	static Controller[] cont = new Controller[4];
	private boolean butt[][] = new boolean[4][10];
	private double[][] axis = new double[4][6];
	private double[][] pov = new double[4][2];
	private boolean multiplayer = true;
	
	private static boolean controlConnect = false;
	
	double interpolation = 0;
	final int TICKS_PER_SECOND = 60;
	final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	final int MAX_FRAMESKIP = 5;
	
	int coolLoops = 0;
	
	public Runner() {
		
		setSize(new Dimension(1280, 720));
		setPreferredSize(new Dimension(1280, 720));
		setFocusable(true);
		addKeyListener(this);
	}

	public static void main(String[] args) {
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
			System.err.println("Controllers not found!");
		}
		
		test = new Room("test");
		test.load();
		Runner game = new Runner();
		
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
	}
	
	@Override
	public void paint(Graphics g) {
		player.getInstance(this);
		
		g.setColor(new Color(255, 0, 255));
		g.fillRect(0, 0, getWidth(), getHeight());
		player.draw(g);
		test.draw(g);
		player.checkCollision(this);
		
		if (controlConnect)
			pollControllers();
		
		
		double nextGameTick = System.currentTimeMillis();
		int loops = 0;
		
		while (System.currentTimeMillis() > nextGameTick &&
				loops < MAX_FRAMESKIP) {
			nextGameTick += SKIP_TICKS;
			loops++;
			System.out.println(loops);
		}
		
		repaint();
		
	}
	
	@Override
	public void update(Graphics g) {
		return;
	}

	@SuppressWarnings("unused")
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(KeyEvent.VK_A == 1 && KeyEvent.VK_D != 1)
			player.left();
		else if(KeyEvent.VK_D == 1 && KeyEvent.VK_A != 1)
			player.right();
		else if(KeyEvent.VK_D != 1 && KeyEvent.VK_A != 1) 
			player.decelerate();
		if(KeyEvent.VK_SPACE == 1 /* && [standing on ground] [temp -->]*/ && player.getDy() == 0)
			player.jump();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		player.decelerate();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void getRoom() {
		//return Room;
	}
	
	public void pollControllers() {
		
		if (multiplayer ) {
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
		System.out.println(axis[0][3]);
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
	
}
