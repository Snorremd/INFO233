package game.monster;

import game.entity.types.Level;
import game.entity.types.Monster;
import game.gfx.SpriteLoader;
import game.util.Direction;

import java.awt.Graphics;

public abstract class AbstractMonster implements Monster{
	protected int column, row, sheetRow, sheetColumn;
	protected Direction facingDirection;
	protected SpriteLoader loader;
	protected Level level;
	
	/**
	 * @param column hvor på brettet monsteret er, (X-aksen)
	 * @param row hvor på brettet monsteret er, (Y-aksen)
	 * @param level brettet hvor monsteret er på
	 * @param loader spriteloaderen for dette monsteret
	 * @param sheetRow hvilken rekke på sheeten vi skal bruke.
	 */
	public AbstractMonster(int column, int row, Level level, SpriteLoader loader, int sheetRow){
		this.column = column;
		this.row = row;
		this.sheetRow = sheetRow;
		this.sheetColumn = 0;
		this.level = level;
		this.loader = loader;
	}
	
	@Override
	public void move(Direction dir, Level l) {
		facingDirection = dir;
		switch(facingDirection){
		case NORTH:
			if(l.walkable(column, row - 1)){
				row -= 1; 
			}
			break;
		case WEST:
			if(l.walkable(column - 1, row)){
				column -=1;
			}
			break;
		case EAST:
			if(l.walkable(column + 1, row)){
				column += 1;
			}
			break;
		case SOUTH:
			boolean walkable = l.walkable(column, row + 1);
			if(walkable){
				row += 1;
			}
			break;
		}
		
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getColumn() {
		return column;
	}

	@Override
	public void setPosition(int column, int row) {
		this.column = column;
		this.row = row;
	}

	@Override
	public void setDirection(Direction dir) {
		this.facingDirection = dir;
		switch(dir){
		case NORTH:
			this.sheetColumn = 3; break;
		case WEST:
			this.sheetColumn = 2; break;
		case EAST:
			this.sheetColumn = 1; break;
		case SOUTH:
			this.sheetColumn = 0; break;
		}
	}

	@Override
	public void render(Graphics gfx) {
		
		gfx.drawImage(loader.getImage(sheetColumn, sheetRow), column * loader.tilesize(), row * loader.tilesize(), null);
	}

	@Override
	public abstract void tick();

}
