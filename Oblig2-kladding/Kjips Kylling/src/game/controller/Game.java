package game.controller;

import game.entity.AliasNotRegisteredException;
import game.entity.SimplePlayer;
import game.entity.TileFactory;
import game.entity.TileLevel;
import game.entity.TileNotRegisteredException;
import game.entity.tiles.IllegalTileException;
import game.entity.types.Level;
import game.entity.types.Player;
import game.gfx.Lerret;
import game.gfx.LerretFactory;
import game.gfx.SpriteLoader;
import game.input.SimpleKeyboard;
import game.util.Direction;
import game.util.PaintingThread;

import java.io.File;
import java.io.IOException;

public class Game {

	protected Lerret lerret;
	protected Level level;
	protected Player player;
	protected SimpleKeyboard keyboard;
	protected PaintingThread painter;
	
	public Game(Adventure adv) throws IOException, TileNotRegisteredException, IllegalTileException, AliasNotRegisteredException{
		this.keyboard = new SimpleKeyboard(this);
		this.level = adv.getLevel(0);
		this.player = new SimplePlayer(new SpriteLoader(new File("art/figur.png"), level.tilesize()));		
		this.lerret = new LerretFactory()
					      .player(player)
					      .level(level)
					      .title("Kjips Kylling")
					      .create();
		
		System.out.printf("Trying to set player to (%d,%d)%n", level.getStartingColumn(), level.getStartingRow());
		player.setPosition(level.getStartingColumn(), level.getStartingRow());
		System.out.printf("Set player to (%d,%d)%n ", player.getColumn(), player.getRow());
		// TODO: Putt inn i konstruktøren eller noe?
		lerret.registerKeyboard(keyboard);
		painter = new PaintingThread(lerret);
	}
	
	public void move(Direction where){
		player.move(where, level);
		level.tick();
	}
	
	public void start (TickType ticking){
		
	}

	public static void main(String[] args) throws Exception {
		/* TODO: Disse tingene skal ut av main-metoden og inn i en egen ting etter hvert som
		 * det blir mer klart hvordan spiller bør startes. */
		SpriteLoader tileSprites = new SpriteLoader(new File ("art/tiles.png"), 64);
		
		TileFactory tf = new TileFactory(tileSprites);
		tf.registerTiles(new File("res/standard-levels.csv"));
		tf.registerAliases(new File("res/standard.alias"));
		
		Level l = TileLevel.load(tf, new File("res/labyrinth.level")); // new GreyLevel(10,10,64);
		System.out.println(l);
		
		new Game(Adventure.fromFile(new File("res/adventures.csv"))[0]);
	}
}
