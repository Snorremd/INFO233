package game.entity;

import game.entity.types.Level;
import game.entity.types.Player;
import game.gfx.SpriteLoader;
import game.util.Direction;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SimplePlayer implements Player {
	SpriteLoader mySprites;
	Direction faces;
	int xTile, yTile, tilesize;
	
	public SimplePlayer(SpriteLoader playersprites){
		this.mySprites = playersprites;
		this.faces = Direction.SOUTH;
		this.xTile = 0;
		this.yTile = 0;
		this.tilesize = playersprites.tilesize();
	}
	
	@Override
	public void render(Graphics gfx) {
		gfx.drawImage(getImg(), xTile * tilesize, yTile * tilesize, null);
	}

	protected BufferedImage getImg(){
		int col = 0;
		int row;
		switch(faces){
			case NORTH:
				row = 0; break;
			case WEST:
				row = 1; break;
			case SOUTH:
				row = 2; break;
			case EAST:
				row = 3; break;
			default:
				row = 0; break;
		}
		return mySprites.getImage(col, row);
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(Direction dir, Level l) {
		faces = dir;
		switch(faces){
		case NORTH:
			if(l.walkable(xTile, yTile - 1)){
				yTile -= 1; 
			}
			break;
		case WEST:
			if(l.walkable(xTile - 1, yTile)){
				xTile -=1;
			}
			break;
		case EAST:
			if(l.walkable(xTile + 1, yTile)){
				xTile += 1;
			}
			break;
		case SOUTH:
			boolean walkable = l.walkable(xTile, yTile + 1);
			if(walkable){
				yTile += 1;
			}
			break;
		}
		
		System.out.printf("Facing %s, at (%d,%d)%n", faces, xTile, yTile);
	}

	@Override
	public void setPosition(int column, int row) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setDirection(Direction dir) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getRow() {
		return yTile;
	}

	@Override
	public int getColumn() {
		// TODO Auto-generated method stub
		return xTile;
	}

}
