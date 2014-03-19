package game.controller;

import game.entity.AliasNotRegisteredException;
import game.entity.PlayerNotInstantiatedException;
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

public class Game {
	protected Player player;
	protected SimpleKeyboard keyboard;
	protected ResourceLoader loader;
	protected GameWindow window;
	protected Level level;

	public Game(ResourceLoader loader) throws SpriteSheetNotFoundException, LevelNotFoundException, TileNotRegisteredException, IllegalTileException, AliasNotRegisteredException, PlayerNotInstantiatedException {
		this.loader = loader;
		this.keyboard = new SimpleKeyboard(this);
		SimplePlayer.init(loader.getSpriteLoader("player"));
		this.player = SimplePlayer.getInstance();		
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
			startLevel();

			long timestamp = System.nanoTime(); /* Aldri bruk System.currentTimeMillis() til denne type ting. Tenk om du sitter på et tog, krysser en tidssone, og så krasjer spillet. */
			int timesPerSecond = 1; /* Hvor mange ganger i sekundet entiteter som ikke er spilleren får gjøre noe. */
			long tickFrequency = 1_000_000_000L / timesPerSecond; /* Her har vi tap av presisjon grunnet heltallsdivisjon. For vårt bruk er dette greit. */
			long levelStartedAt = timestamp; 
			/* Sjekker at du ikke har vunnet. Da skal spillet laste neste brett. */
			boolean done = false;
			while(!done){
				done = (player.getColumn() == level.getGoalColumn() && 
					    player.getRow() == level.getGoalRow());
				
				if(level.isPlaceDeadly(player.getColumn(), player.getRow())){
					boolean restart = window.popupDeath();
					if(restart){
						startLevel();
						continue;
					}
					else{
						return;
					}
				}
				long timeSinceLastOp = System.nanoTime() - timestamp;

				if(timeSinceLastOp >= tickFrequency){
					level.tick();
					timestamp = System.nanoTime();
				}
			}
			long levelEndedAt = System.nanoTime();
			long timeTaken = (levelEndedAt - levelStartedAt) / 100_000_000L;

			window.popupVictory();
			window.popupGeneric("Tid brukt", String.format("Du har brukt %d tidels sekunder", timeTaken));
			currentLevel++;
		}
		window.popupGameComplete();
	}
	private void startLevel(){
		player.setPosition(level.getStartingColumn(), level.getStartingRow());
		player.setDirection(Direction.SOUTH);
		window.loadLevel(level);
	}
	
	public void shutdown(){
		window.dispose();
		window = null;
	}
}
