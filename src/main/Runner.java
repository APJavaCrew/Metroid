package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.Player;

public class Runner extends JPanel implements KeyListener {
	
	private Player player = new Player();
	
	public Runner() {
		setSize(new Dimension(1280, 720));
		setPreferredSize(new Dimension(1280, 720));
	}
	
	@Override
	public void paint(Graphics g) {
		player.draw(g);
		
		repaint();
		
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}

	@SuppressWarnings("unused")
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(KeyEvent.VK_A == 1 && KeyEvent.VK_D != 1) {
			if(Math.abs(player.getDx()) < Constants.MAX_RUN_SPEED) {
				player.setDx(player.getDx() - Constants.RUN_ACCEL);
			} else {
				player.setDx(-1);
			}
			
		} else if(KeyEvent.VK_D == 1 && KeyEvent.VK_A != 1) {
			if(Math.abs(player.getDx()) < Constants.MAX_RUN_SPEED) {
				player.setDx(player.getDx() + Constants.RUN_ACCEL);
			} else {
				player.setDx(1);
			}
		} else if(KeyEvent.VK_D != 1 && KeyEvent.VK_A != 1) {
			if(player.getDx() > Constants.BRAKE_DEADZONE)
				player.setDx(player.getDx() - Constants.BRAKE_ACCEL);
			else if(player.getDx() < (-1 * Constants.BRAKE_DEADZONE))
				player.setDx(player.getDx() + Constants.BRAKE_ACCEL);
			else
				player.setDx(0);
		}
		if(KeyEvent.VK_SPACE == 1 /* && [standing on ground] [temp -->]*/ && player.getDy() == 0) {
			player.setDy(1);
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(player.getDx() > Constants.BRAKE_DEADZONE)
			player.setDx(player.getDx() - Constants.BRAKE_ACCEL);
		else if(player.getDx() < (-1 * Constants.BRAKE_DEADZONE))
			player.setDx(player.getDx() + Constants.BRAKE_ACCEL);
		else
			player.setDx(0);
		if(player.getDy() < Constants.TERMINAL_VELOCITY /*&& player is not on the ground*/) {
			player.setDy(player.getDy() + Constants.GRAVITY_ACCEL);
		} else if(player.getDy() >= Constants.TERMINAL_VELOCITY /*&& player is not on the ground*/) {
			player.setDy(Constants.TERMINAL_VELOCITY);
		} else /*player is on the ground*/ {
			player.setDy(0);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
