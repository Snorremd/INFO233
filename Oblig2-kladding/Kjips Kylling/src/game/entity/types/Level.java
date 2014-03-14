package game.entity.types;

import game.util.Direction;


public interface Level extends Entity {
	public boolean move(Entity who, Direction where);
	public boolean walkable(int column, int row);
}
