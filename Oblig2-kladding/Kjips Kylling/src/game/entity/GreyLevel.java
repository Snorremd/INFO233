package game.entity;

import java.awt.Color;
import java.awt.Graphics;

import game.entity.types.Entity;
import game.entity.types.Level;
import game.util.Direction;

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
	public boolean move(Entity who, Direction where) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean walkable(int column, int row) {
		return checkLevelBounds(column, row);
	}
	
	protected boolean checkLevelBounds(int column, int row){
		return !(column < 0 || column > width - 1 || row < 0 || row > height - 1);
	}

}
