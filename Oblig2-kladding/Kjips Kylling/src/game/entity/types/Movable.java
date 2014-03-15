package game.entity.types;

import game.util.Direction;

public interface Movable {
	public void move(Direction dir, Level l);
	public int getRow();
	public int getColumn();
	public void setPosition(int column, int row);
	public void setDirection(Direction dir);
}
