package game.util;

import game.entity.AliasNotRegisteredException;
import game.entity.TileFactory;
import game.entity.TileLevel;
import game.entity.TileNotRegisteredException;
import game.entity.tiles.IllegalTileException;
import game.gfx.SpriteLoader;

public interface ResourceLoader {
	public TileLevel getLevel(String name) throws LevelNotFoundException, TileNotRegisteredException, IllegalTileException, AliasNotRegisteredException;
	public TileLevel getLevel(int number) throws LevelNotFoundException, TileNotRegisteredException, IllegalTileException, AliasNotRegisteredException;
	public SpriteLoader getSpriteLoader (String name) throws SpriteSheetNotFoundException;
	public TileFactory getTileFactory();
	public int getNumLevels();
}
