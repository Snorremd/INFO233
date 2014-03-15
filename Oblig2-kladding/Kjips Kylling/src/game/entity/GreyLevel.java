package game.entity;

import game.entity.types.Level;
import game.entity.types.Tile;

import java.awt.Color;
import java.awt.Graphics;

public class GreyLevel implements Level {
	int width, height, tilesize;
	
	public GreyLevel(int width, int height, int tilesize){
		this.width = width;
		this.height = height;
		this.tilesize = tilesize;
	}
	
	@Override
	public void render(Graphics gfx) {
		Color pre = gfx.getColor();
		gfx.setColor(new Color(0xCDBA96)); // Hvetefarget
		gfx.fillRect(0, 0, width * tilesize, height * tilesize);
		gfx.setColor(pre);
	}

	@Override
	public void tick() {
		// NOOP
	}

	@Override
	public boolean walkable(int column, int row) {
		return checkLevelBounds(column, row);
	}
	
	protected boolean checkLevelBounds(int column, int row){
		return !(column < 0 || column > width - 1 || row < 0 || row > height - 1);
	}

	@Override
	public Tile tileAt(int column, int row) {
		// greylevel har ikke noen tiles, så den returnerer null
		return null;
	}

}
