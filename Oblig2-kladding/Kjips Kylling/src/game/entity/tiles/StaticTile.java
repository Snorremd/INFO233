package game.entity.tiles;

import java.awt.Graphics;

import game.entity.types.Level;
import game.entity.types.Tile;
import game.gfx.SpriteLoader;
import game.util.Direction;
import game.util.Mover;

public class StaticTile implements Tile{
	protected int row, col, tilesize;
	protected Direction dir;
	protected SpriteLoader sl;
	protected int spriteX, spriteY;
	protected boolean walkable, pushable, lethal;
	
	/**
	 * Denne er relativt grusom å bruke. Bruke TileBuilder istedenfor.
	 * Mye enklere å holde orden på ting med.
	 * @param row raden på brettet flisen skal være på
	 * @param col kolonnen på brettet flisen skal være på
	 * @param tilesize størrelsen på flisen
	 * @param spriteloader hvor den skal få fatt på spriten sin fra.
	 * @param spriteX hvilken rad i arket spriten sitter på
	 * @param spriteY hvilken kolonne i arket spriten sitter på
	 * @param walkable om du kan gå på den. Merk at en tile ikke kan være walkable og pushable.
	 * @param pushable om du kan skubbe på blokken. Merk at en tile ikke kan være walkable og pushable.
	 * @param lethal om du dør om du tar i den. (Tenk lava, syrebad, etc.)
	 * @throws IllegalTileException om en tile er walkable og pushable
	 */
	public StaticTile(int row, int col, SpriteLoader spriteloader, int spriteX, int spriteY, boolean walkable, boolean pushable, boolean lethal) throws IllegalTileException{
		if(walkable && pushable){
			throw new IllegalTileException("A Tile cannot be walkable and pushable");
		}
		
		this.row = row;
		this.col = col;
		this.sl = spriteloader;
		this.spriteX = spriteX;
		this.spriteY = spriteY;
		this.walkable = walkable;
		this.pushable = pushable;
		this.lethal = lethal;
		
	}
	
	@Override
	public void render(Graphics gfx) {
		gfx.drawImage(sl.getImage(spriteX, spriteY), col*sl.tilesize(), row*sl.tilesize(), null);
	}

	@Override
	public void move(Direction dir, Level l) {
		int[] pos = Mover.position(dir, getColumn(), getRow());
		if(l.walkable(pos[Mover.COLUMN], pos[Mover.ROW])){
			this.col = pos[Mover.COLUMN];
			this.row  = pos[Mover.ROW];
		}
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getColumn() {
		return col;
	}

	@Override
	public void setPosition(int column, int row) {
		this.col = column;
		this.row = row;
	}

	@Override
	public void setDirection(Direction dir) {
		this.dir = dir;
	}

	@Override
	public boolean isLethal() {
		return lethal;
	}

	@Override
	public boolean isWalkable() {
		return walkable;
	}

	@Override
	public boolean isPushable() {
		return pushable;
	}
	
	public String toString(){
		return String.format("Static tile at (%d,%d), %s, %s, %s",
				getColumn(), getRow(),
				walkable? "walkable" : "not walkable",
				pushable? "pushable" : "not pushable",
				lethal?   "lethal"   : "not lethal");
	}

}
