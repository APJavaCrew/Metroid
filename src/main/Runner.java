package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.Player;
import level.Room;

public class Runner extends JPanel implements KeyListener {
	
	private Player player = new Player();
	static Room test;
	
	public Runner() {
		setSize(new Dimension(1280, 720));
		setPreferredSize(new Dimension(1280, 720));
		setFocusable(true);
		addKeyListener(this);
	}

	public static void main(String[] args) {
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
		player.draw(g);
<<<<<<< HEAD
		test.draw(g);
=======
		player.checkCollision(this);
>>>>>>> yes
		
		repaint();
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
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
		if(/*player is not on the ground [temp -->]*/true) {
			player.fall();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void getRoom() {
		//return Room;
	}
}
