package game.controller;

import game.entity.AliasNotRegisteredException;
import game.entity.SimplePlayer;
import game.entity.TileNotRegisteredException;
import game.entity.tiles.IllegalTileException;
import game.entity.types.Level;
import game.entity.types.Player;
import game.input.SimpleKeyboard;
import game.util.Direction;
import game.util.LevelNotFoundException;
import game.util.ResourceLoader;
import game.util.SpriteSheetNotFoundException;
import game.view.GameWindow;

import javax.swing.JOptionPane;

public class Game {
	protected Player player;
	protected SimpleKeyboard keyboard;
	protected ResourceLoader loader;
	protected GameWindow window;
	protected Level level;

	public Game(ResourceLoader loader) throws SpriteSheetNotFoundException, LevelNotFoundException, TileNotRegisteredException, IllegalTileException, AliasNotRegisteredException {
		this.loader = loader;
		this.keyboard = new SimpleKeyboard(this);
		this.player = new SimplePlayer(loader.getSpriteLoader("player"));		
		window = new GameWindow(keyboard, player);
	}

	public void move(Direction where){
		player.move(where, level);
	}

	public void start() throws LevelNotFoundException, TileNotRegisteredException, IllegalTileException, AliasNotRegisteredException{
		int currentLevel = 1;

		while(currentLevel <= loader.getNumLevels()){

			/* Sett brettet til nytt brett hver gang du klarer ett brett.
			 * Her er det plenty med muligheter til utvidelse av logikken. */
			level = loader.getLevel(currentLevel);
			window.loadLevel(level);
			player.setPosition(level.getStartingColumn(), level.getStartingRow());

			long timestamp = System.nanoTime();
			int timesPerSecond = 1; /* Hvor mange ganger i sekundet entiteter som ikke er spilleren får bevege seg. */
			long tickFrequency = 1_000_000_000L / timesPerSecond;

			boolean won = false;
			while(!won){
				won = player.getColumn() == level.getGoalColumn() &&
						player.getRow() == level.getGoalRow();

				long timeSinceLastOp = System.nanoTime() - timestamp;

				if(timeSinceLastOp >= tickFrequency){
					level.tick();
					timestamp = System.nanoTime();
				}
			}
			JOptionPane.showMessageDialog(window, "DU HAR VUNNET", "SEIER!", JOptionPane.INFORMATION_MESSAGE);
			currentLevel++;
		}
		JOptionPane.showMessageDialog(window, "Spill rundet", "Ferdig!", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}
}
