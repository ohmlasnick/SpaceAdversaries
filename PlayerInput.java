package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerInput implements KeyListener {
	
	public boolean up, down, left, right, fire, START;

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			up = true;
		}
		if (key == KeyEvent.VK_DOWN) {
			down = true;
		}
		if (key == KeyEvent.VK_LEFT) {
			left = true;
		}
		if (key == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if (key == KeyEvent.VK_SPACE) {
			fire = true;
		}
		if (key == KeyEvent.VK_B) {
			START = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			up = false;
		}
		if (key == KeyEvent.VK_DOWN) {
			down = false;
		}
		if (key == KeyEvent.VK_LEFT) {
			left = false;
		}
		if (key == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if (key == KeyEvent.VK_SPACE) {
			fire = false;
		}
	}

}
