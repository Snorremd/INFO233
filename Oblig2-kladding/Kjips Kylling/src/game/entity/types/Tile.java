package game.entity.types;

public interface Tile extends Paintable, Movable{
	public boolean isLethal();
	public boolean isWalkable();
}
