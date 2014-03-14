package game.input;

import game.controller.Game;
import game.util.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SimpleKeyboard implements KeyListener {
	Game game;
	
	public SimpleKeyboard(Game game){
		this.game = game;
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			game.move(Direction.NORTH); break;
			
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			game.move(Direction.WEST); break;
		
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			game.move(Direction.EAST); break;
			
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			game.move(Direction.SOUTH); break;
		
		
		default:
			System.out.println("Unknown keypress: " + e);
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		return;
	}
	
	public void keyTyped(KeyEvent arg0) {
		return;
	}
}
