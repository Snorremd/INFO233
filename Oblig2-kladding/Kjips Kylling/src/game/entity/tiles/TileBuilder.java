package game.entity.tiles;

import game.gfx.SpriteLoader;
import game.util.Direction;


public class TileBuilder {
	int row, col, spriteX, spriteY, tilesize;
	SpriteLoader spriteloader;
	boolean walkable, pushable, lethal;

	/*
	 * Alle disse metodene er laget automatisk av GNU Emacs.
	 * Nei, jeg har ikke giddet å lage disse for hånd. ;)
	 */
	
    public TileBuilder spriteloader(SpriteLoader spriteloader){
	this.spriteloader = spriteloader;
	return this;
    }
    
	public TileBuilder walkable(boolean walkable){
		this.walkable = walkable;
		return this;
	}

	public TileBuilder pushable(boolean pushable){
		this.pushable = pushable;
		return this;
	}

	public TileBuilder lethal(boolean lethal){
		this.lethal = lethal;
		return this;
	}
	
	public TileBuilder row(int row){
		this.row = row;
		return this;
	}

	public TileBuilder col(int col){
		this.col = col;
		return this;
	}

	public TileBuilder spriteX(int spriteX){
		this.spriteX = spriteX;
		return this;
	}

	public TileBuilder spriteY(int spriteY){
		this.spriteY = spriteY;
		return this;
	}

	public TileBuilder tilesize(int tilesize){
		this.tilesize = tilesize;
		return this;
	}
	
	public StaticTile create() throws IllegalTileException{
		return new StaticTile(row, col, tilesize, spriteloader, spriteX, spriteY, walkable, pushable, lethal);
	}
}
