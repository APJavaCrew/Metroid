package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Runner extends JPanel implements KeyListener {
	
	public Runner() {
		setSize(new Dimension(1280, 720));
		setPreferredSize(new Dimension(1280, 720));
		setFocusable(true);
		addKeyListener(this);
	}

	public static void main(String[] args) {
		
		Runner game = new Runner();
		
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
