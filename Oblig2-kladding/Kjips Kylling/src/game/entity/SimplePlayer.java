package game.entity;

import game.entity.types.Level;
import game.entity.types.Player;
import game.gfx.SpriteLoader;
import game.util.Direction;
import game.util.Mover;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SimplePlayer implements Player {
	private SpriteLoader playerSprites = null;
	Direction faces;
	int xTile, yTile, tilesize;
	
	private static SimplePlayer ONLY_PLAYER = null;
	
	public static boolean init(SpriteLoader loader){
		if(null == ONLY_PLAYER){
			ONLY_PLAYER = new SimplePlayer(loader);
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * For å få fatt på SimplePlayer kaller du getInstance()
	 * Alle kall til SimplePlayer#getInstance() returnerer den samme instansen.
	 * @return Den ene unike instansen av SimplePlayer.
	 * @throws PlayerNotInstantiatedException en RuntimeException dersom SimplePlayer#init() ikke har blitt kalt før du ber om getInstance().
	 *  Du MÅ være sikker på at init blir kalt først.
	 */
	public static SimplePlayer getInstance() throws PlayerNotInstantiatedException {
		if(null == ONLY_PLAYER){
			throw new PlayerNotInstantiatedException("SimplePlayer.init() seems to not have been called successfully. This should be done by a controller at startup");
		}
		return ONLY_PLAYER;
	}
	
	private SimplePlayer(SpriteLoader loader){
		this.faces = Direction.SOUTH;
		this.xTile = 0;
		this.yTile = 0;
		this.playerSprites = loader;
		this.tilesize = playerSprites.tilesize();
	}
	
	@Override
	public void render(Graphics gfx) {
		gfx.drawImage(getImg(), xTile * tilesize, yTile * tilesize, null);
	}

	/**
	 * Ment til internt bruk.
	 * Dersom du arver klassen og overkjører denne vil det ha konsekvenser for rendringen av spilleren.
	 * @return bilde som skal brukes dersom SimplePlayer skal males akkurat nå.
	 */
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
		return playerSprites.getImage(col, row);
	}
	
	@Override
	public void move(Direction dir, Level l) {
		setDirection(dir);
		int[] pos = Mover.position(dir, getColumn(), getRow());
		if(l.walkable(pos[Mover.COLUMN], pos[Mover.ROW])){
			xTile = pos[Mover.COLUMN];
			yTile = pos[Mover.ROW];
		}
		
		System.out.printf("Facing %s, at (%d,%d)%n", faces, xTile, yTile);
	}

	@Override
	public void setPosition(int column, int row) {
		this.xTile = column; this.yTile = row;
	}

	@Override
	public void setDirection(Direction dir) {
		this.faces = dir;
	}

	@Override
	public int getRow() {
		return yTile;
	}

	@Override
	public int getColumn() {
		return xTile;
	}
}
